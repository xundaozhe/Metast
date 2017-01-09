package com.iuunited.myhome.mapdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.iuunited.myhome.R;
import com.iuunited.myhome.base.BaseFragmentActivity;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationListener;
import com.mapbox.mapboxsdk.location.LocationServices;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
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
 * @time 2016/12/21 14:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes $TODO$
 * Created by xundaozhe on 2016/12/21.
 */

public class TwoActivity extends BaseFragmentActivity implements MapView.OnMapChangedListener{

    private MapView mapView;
    private MapboxMap map;
    private LocationServices locationServices;
    private Marker droppedMarker;
    private ImageView hoveringMarker;
    private Button selectLocationButton;
    private LocationListener locationListener;

    private String MAPBOX_ACCESS_TOKEN = "";

    private static final int PERMISSIONS_LOCATION = 0;
    private static final String TAG = "TwoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, getString(R.string.accessToken));
        setContentView(R.layout.activity_two);
        locationServices = LocationServices.getLocationServices(TwoActivity.this);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                map = mapboxMap;

                // Once map is ready, we want to position the camera above the user location. We
                // first check that the user has granted the location permission, then we call
                // setInitialCamera.
                if (!locationServices.areLocationPermissionsGranted()) {
                    ActivityCompat.requestPermissions(TwoActivity.this, new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
                } else {
                    setInitialCamera();
                }
            }


        });

        hoveringMarker = new ImageView(this);
        hoveringMarker.setImageResource(R.drawable.red_marker);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        hoveringMarker.setLayoutParams(params);
        mapView.addView(hoveringMarker);

        // Button for user to drop marker or to pick marker back up.
        selectLocationButton = (Button) findViewById(R.id.select_location_button);
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map != null) {
                    if (droppedMarker == null) {
                        // We first find where the hovering marker position is relative to the map.
                        // Then we set the visibility to gone.
                        float coordinateX = hoveringMarker.getLeft() + (hoveringMarker.getWidth() / 2);
                        float coordinateY = hoveringMarker.getBottom();
                        float[] coords = new float[] {coordinateX, coordinateY};
                        final LatLng latLng = map.getProjection().fromScreenLocation(new PointF(coords[0], coords[1]));
                        hoveringMarker.setVisibility(View.GONE);

                        // Transform the appearance of the button to become the cancel button
                        selectLocationButton.setBackgroundColor(
                                ContextCompat.getColor(TwoActivity.this, R.color.colorAccent));
                        selectLocationButton.setText("Cancel");

                        // Create the marker icon the dropped marker will be using.
                        IconFactory iconFactory = IconFactory.getInstance(TwoActivity.this);
                        Drawable iconDrawable = ContextCompat.getDrawable(TwoActivity.this, R.drawable.red_marker);
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
                                ContextCompat.getColor(TwoActivity.this, R.color.colorPrimary));
                        selectLocationButton.setText("Select a location");

                        // Lastly, set the hovering marker back to visible.
                        hoveringMarker.setVisibility(View.VISIBLE);
                        droppedMarker = null;
                    }
                }
            }
        });
    }

    private void reverseGeocode(LatLng point) {
        // This method is used to reverse geocode where the user has dropped the marker.
        try {
            MapboxGeocoding client = new MapboxGeocoding.Builder()
                    .setAccessToken(getString(R.string.accessToken))
                    .setCoordinates(Position.fromCoordinates(point.getLongitude(), point.getLatitude()))
                    .setGeocodingType(GeocodingCriteria.TYPE_ADDRESS)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    List<CarmenFeature> results = response.body().getFeatures();
                    if (results.size() > 0) {
                        CarmenFeature feature = results.get(0);
                        // If the geocoder returns a result, we take the first in the list and update
                        // the dropped marker snippet with the information. Lastly we open the info
                        // window.
                        if (droppedMarker != null) {
                            double longitude = droppedMarker.getPosition().getLongitude();
                            double latitude = droppedMarker.getPosition().getLatitude();
                            Log.e("dropMarker",longitude+","+latitude+"-----------");
                            droppedMarker.setSnippet(feature.getPlaceName());
                            map.selectMarker(droppedMarker);
                        }

                    } else {
                        if (droppedMarker != null) {
                            droppedMarker.setSnippet("No results");
                            map.selectMarker(droppedMarker);
                        }
                    }
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

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setInitialCamera();
            }
        }
    }

    private void setInitialCamera() {
// Method is used to set the initial map camera position. Should only be called once when
        // the map is ready. We first try using the users last location so we can quickly set the
        // camera as fast as possible.
        if (locationServices.getLastLocation() != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationServices.getLastLocation()), 16));
        }

        // This location listener is used in a very specific use case. If the users last location is
        // unknown we wait till the GPS locates them and position the camera above.
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    // Move the map camera to where the user location is
                    map.setCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(location))
                            .zoom(16)
                            .build());
                    locationServices.removeLocationListener(this);
                }
            }
        };
        locationServices.addLocationListener(locationListener);
        // Enable the location layer on the map and track the user location until they perform a
        // map gesture.
        map.setMyLocationEnabled(true);
        map.getTrackingSettings().setMyLocationTrackingMode(MyLocationTracking.TRACKING_FOLLOW);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationListener != null) {
            locationServices.removeLocationListener(locationListener);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapChanged(int change) {
        Log.e("change","111111111111");
    }
}
