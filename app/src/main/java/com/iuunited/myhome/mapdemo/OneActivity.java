package com.iuunited.myhome.mapdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.iuunited.myhome.Manifest;
import com.iuunited.myhome.R;
import com.iuunited.myhome.adapter.MapListAdapter;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.MapPoiBean;
import com.iuunited.myhome.util.PermissionUtils;
import com.iuunited.myhome.util.ToastUtils;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationListener;
import com.mapbox.mapboxsdk.location.LocationServices;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.ServicesException;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.geocoding.v5.MapboxGeocoding;
import com.mapbox.services.geocoding.v5.models.CarmenFeature;
import com.mapbox.services.geocoding.v5.models.GeocodingResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneActivity extends BaseFragmentActivity implements OnMapReadyCallback, GeocoderAutoCompleteView.OnFeatureListener, View.OnTouchListener {
    private MapView mapView;
    private LocationManager locationMannger;

    private LocationServices locationServices;
    private Marker droppedMarker;
    private ImageView hoveringMarker;
    private Button selectLocationButton;
    private LocationListener mLocationListener;

    private String MAPBOX_ACCESS_TOKEN = "";

    private static final int PERMISSIONS_LOCATION = 0;
    private static final String TAG = "TwoActivity";

    private double lat;
    private double lon;

    private MapboxMap map;
    private GeocoderAutoCompleteView autocomplete;

    private ImageView iv_user_location;

    private int touchAction = 0;

    private ListView mapListview;
    private List<MapPoiBean> mDatas = new ArrayList<>();
    private MapListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        locationServices = LocationServices.getLocationServices(OneActivity.this);
        mapView = (MapView) findViewById(R.id.mapView);
        getLocation();
        MAPBOX_ACCESS_TOKEN = getResources().getString(R.string.accessToken);
        mapView.setAccessToken(MAPBOX_ACCESS_TOKEN);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
//        mapView.setZoom(14);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.setOnTouchListener(this);


//        getMyLocation();
        autocomplete = (GeocoderAutoCompleteView) findViewById(R.id.query);
        autocomplete.setAccessToken(MAPBOX_ACCESS_TOKEN);
        autocomplete.setOnFeatureListener(this);

        iv_user_location = (ImageView) findViewById(R.id.iv_user_location);
        iv_user_location.setOnClickListener(this);

        /********添加中心图标********/
        hoveringMarker = new ImageView(this);
        hoveringMarker.setImageResource(R.drawable.red_marker);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        hoveringMarker.setLayoutParams(params);
        mapView.addView(hoveringMarker);

        selectLocationButton = (Button) findViewById(R.id.select_location_button);
        mapListview = (ListView) findViewById(R.id.map_lv);
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new MapListAdapter(this, mDatas);
            mapListview.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void reverseGeocode(LatLng point) {
        // This method is used to reverse geocode where the user has dropped the marker.
        try {
            MapboxGeocoding client = new MapboxGeocoding.Builder()
                    .setAccessToken(getString(R.string.accessToken))
                    .setCoordinates(Position.fromCoordinates(point.getLongitude(), point.getLatitude()))
                    .setGeocodingType(GeocodingCriteria.TYPE_POI)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                    touchAction = 1;
                    List<CarmenFeature> results = response.body().getFeatures();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            CarmenFeature carmenFeature = results.get(i);
                            String placeName = carmenFeature.getPlaceName();
                            String address = carmenFeature.getText();
                            String userLoca = placeName.substring(placeName.indexOf(",") + 1);
                            MapPoiBean poiBean = new MapPoiBean();
                            poiBean.setAddress(address);
                            poiBean.setPlaceName(userLoca);
                            mDatas.add(poiBean);
                        }
                        CarmenFeature feature = results.get(0);
//                        String placeName = feature.getPlaceName();
//                        String substring = placeName.substring(placeName.indexOf(",")+1);
                        // If the geocoder returns a result, we take the first in the list and update
                        // the dropped marker snippet with the information. Lastly we open the info
                        // window.
                        if (droppedMarker != null) {
                            double longitude = droppedMarker.getPosition().getLongitude();
                            double latitude = droppedMarker.getPosition().getLatitude();
                            Log.e("dropMarker", longitude + "," + latitude + "-----------");
                            droppedMarker.setSnippet(feature.getPlaceName());
                            map.selectMarker(droppedMarker);
                        }

                    } else {
                        if (droppedMarker != null) {
                            droppedMarker.setSnippet("No results");
                            map.selectMarker(droppedMarker);
                        }
                    }
                    setAdapter();
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    Log.e(TAG, "Geocoding Failure: " + throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            Log.e(TAG, "Error geocoding: " + servicesException.toString());
            servicesException.printStackTrace();
        }
    }


    /**
     * 使用android原生API获取用户当前位置
     */
    public void getLocation() {
        locationMannger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationMannger.getProviders(true);
        String proviser;
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            proviser = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            proviser = LocationManager.NETWORK_PROVIDER;
        } else {
            ToastUtils.showLongToast(OneActivity.this, "无法定位!");
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationMannger.getLastKnownLocation(proviser);
        if (location != null) {
            //显示当前设备的位置信息
            lat = location.getLatitude();
            lon = location.getLongitude();
            Log.e("mapboxLocation", location.getLatitude() + location.getLongitude() + "location");
        }
        locationMannger.requestLocationUpdates(proviser, 5000, 1, locationListener);
    }

    android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationMannger != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationMannger.removeUpdates(locationListener);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.map = mapboxMap;
        map.setStyle(Style.MAPBOX_STREETS);
//        map.addMarker(new MarkerOptions()//添加标记
//        .position(new LatLng(23.107040,113.274515))
//        .title("2的位置"));
//        userLocation();
        if(map!=null) {
            toggleGps(true);
        }
    }

    private void toggleGps(boolean enableGps) {
        if(enableGps) {
            if(locationServices.areLocationPermissionsGranted()) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
            }else{
                enableLocation(true);
            }
        }
    }

    private void enableLocation(boolean enabled) {
        if(enabled) {
            Location lastLocation = locationServices.getLastLocation();
            if(lastLocation!=null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation),16));
            }
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(location!=null) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location), 16));
                        locationServices.removeLocationListener(this);
                    }
                }
            };
            locationServices.addLocationListener(mLocationListener);
        }
        map.setMyLocationEnabled(enabled);
    }

//    private void userLocation() {
//        map.setMyLocationEnabled(true);
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon))
//                .zoom(16)
//                .build();
//        cameraPosition.toString();
//        Log.e("position",cameraPosition.toString());
//        String s = map.getMyLocation().toString();
//        Log.e("position",s+"---------------------------");
//
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    @Override
    public void OnFeatureClick(CarmenFeature feature) {
        Position position = feature.asPosition();
        updateMap(position.getLatitude(),position.getLongitude());
    }

    private void updateMap(double latitude, double longitude) {
        map.addMarker(new MarkerOptions()
        .position(new LatLng(latitude,longitude))
        .title("search"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude,longitude))
                .zoom(16)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),500,null);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_location:
                cleanDropMarker();
//                userLocation();
                toggleGps(true);
                mapListview.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void cleanDropMarker(){
        if(touchAction == 1) {
            if(droppedMarker!=null) {

                map.removeMarker(droppedMarker);

                // Switch the button apperance back to select a location.
                selectLocationButton.setBackgroundColor(
                        ContextCompat.getColor(OneActivity.this, R.color.colorPrimary));
                selectLocationButton.setText("Select a location");

                // Lastly, set the hovering marker back to visible.
                hoveringMarker.setVisibility(View.VISIBLE);
                droppedMarker = null;
                mDatas.clear();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                cleanDropMarker();

                break;
            case MotionEvent.ACTION_UP:
                if (map != null) {
                    if (droppedMarker == null) {
                        // We first find where the hovering marker position is relative to the map.
                        // Then we set the visibility to gone.
                        mapView.computeScroll();
                        float coordinateX = hoveringMarker.getLeft() + (hoveringMarker.getWidth() / 2);
                        float coordinateY = hoveringMarker.getBottom();
                        float[] coords = new float[]{coordinateX, coordinateY};
                        final LatLng latLng = map.getProjection().fromScreenLocation(new PointF(coords[0], coords[1]));
                        hoveringMarker.setVisibility(View.GONE);

                        // Transform the appearance of the button to become the cancel button
                        selectLocationButton.setBackgroundColor(
                                ContextCompat.getColor(OneActivity.this, R.color.colorAccent));
                        selectLocationButton.setText("Cancel");

                        // Create the marker icon the dropped marker will be using.
                        IconFactory iconFactory = IconFactory.getInstance(OneActivity.this);
                        Drawable iconDrawable = ContextCompat.getDrawable(OneActivity.this, R.drawable.red_marker);
                        Icon icon = iconFactory.fromDrawable(iconDrawable);

                        // Placing the marker on the map as soon as possible causes the illusion
                        // that the hovering marker and dropped marker are the same.
                        droppedMarker = map.addMarker(new MarkerViewOptions().position(latLng).icon(icon));

                        // Finally we get the geocoding information
                        reverseGeocode(latLng);
                    } else {
                        // When the marker is dropped, the user has clicked the button to cancel.
                        // Therefore, we pick the marker back up.
                        map.removeMarker(droppedMarker);

                        // Switch the button apperance back to select a location.
                        selectLocationButton.setBackgroundColor(
                                ContextCompat.getColor(OneActivity.this, R.color.colorPrimary));
                        selectLocationButton.setText("Select a location");

                        // Lastly, set the hovering marker back to visible.
                        hoveringMarker.setVisibility(View.VISIBLE);
                        droppedMarker = null;
                    }
                    if(mapListview.getVisibility() == View.INVISIBLE) {
                        mapListview.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocation(true);
            }
        }
    }


}