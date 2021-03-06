package com.iuunited.myhome.ui.project.professional;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.iuunited.myhome.bean.MapPoiBean;
import com.iuunited.myhome.mapdemo.OneActivity;
import com.iuunited.myhome.ui.home.ProListActivity;
import com.iuunited.myhome.util.IntentUtil;
import com.iuunited.myhome.util.ToastUtils;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author xundaozhe
 * @version $Rev$
 * @time 2017/1/13 17:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2017/1/13.
 */

public class ProMapActivity extends BaseFragmentActivity implements OnMapReadyCallback, GeocoderAutoCompleteView.OnFeatureListener, MapboxMap.OnCameraChangeListener {

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
    private RelativeLayout iv_back;
    private TextView tv_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_map);
        setColor(this,R.color.myHomeBlue);
        locationServices = LocationServices.getLocationServices(ProMapActivity.this);
        mapView = (MapView) findViewById(R.id.mapView);
        getLocation();
        MAPBOX_ACCESS_TOKEN = getResources().getString(R.string.accessToken);
        mapView.setAccessToken(MAPBOX_ACCESS_TOKEN);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
//        mapView.setZoom(14);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
        tv_list = (TextView) findViewById(R.id.tv_list);
        iv_back.setOnClickListener(this);
        tv_list.setOnClickListener(this);
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
            ToastUtils.showLongToast(ProMapActivity.this, "无法定位!");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_location:
//                userLocation();
                toggleGps(true);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_list:
                IntentUtil.startActivity(this,ProListActivity.class);
                break;
        }
    }

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
        map.setOnCameraChangeListener(this);
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
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocation(true);
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition position) {

    }
}
