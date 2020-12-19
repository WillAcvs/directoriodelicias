package com.directoriodelicias.apps.delicias.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.directoriodelicias.apps.delicias.GPS.DirectionPointerLinster;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.GPS.GetPathFromLocation;
import com.directoriodelicias.apps.delicias.GPS.GoogleDirection;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.utils.MapsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.Timer;
import java.util.TimerTask;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class MapDirectionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Timer timer;
    LatLng destinationPosition;
    LatLng myPosition;

    // private GoogleMap map;
    private GoogleMap mMap;
    private GoogleDirection gd;

    private Double lng, distance, lat;
    private String image_url;

    private GoogleApiClient mGoogleApiClient;
    private Toolbar toolbar;


    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initToolbar();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap = googleMap;
        initMapping();

    }

    private void initMapping() {

        //GET TREAD GEO POINT FROM INTENT
        Intent intent = getIntent();
        lat = Double.parseDouble(intent.getStringExtra("latitude"));
        lng = Double.parseDouble(intent.getStringExtra("longitude"));
        distance = Double.parseDouble(intent.getStringExtra("distance"));

        try {
            image_url = intent.getStringExtra("image_url");
        } catch (Exception e) {
            image_url = null;
        }


        String traderName = intent.getStringExtra("name");

        APP_TITLE_VIEW.setText(traderName);

        destinationPosition = new LatLng(lat, lng);

        //INITIALIZE MY LOCATION
        GPStracker trackMe = new GPStracker(this);
        myPosition = new LatLng(trackMe.getLatitude(), trackMe.getLongitude());

        if (mMap != null) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 18));
            mMap.getUiSettings().setZoomControlsEnabled(true);


            addCustomMarker(destinationPosition, mMap);

            /*mMap.addMarker(new MarkerOptions()
                    .title(getApplicationContext().getString(R.string.your_destination))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(myPosition));*/


            gd = new GoogleDirection(this);


            if (ServiceHandler.isNetworkAvailable(this)) {
                try {
                    gd = new GoogleDirection(this);
                    //My Location
                    int distance_config = Integer.parseInt(getResources().getString(R.string.distance_max_display_route));
                    if (distance_config == -1 || distance <= distance_config) {

                        gd.setOnDirectionResponseListener(new GoogleDirection.OnDirectionResponseListener() {

                            @Override
                            public void onResponse(String status, Document doc, GoogleDirection gd) {
                                new GetPathFromLocation(myPosition, destinationPosition, new DirectionPointerLinster() {
                                    @Override
                                    public void onPath(PolylineOptions polyLine) {
                                        mMap.addPolyline(polyLine);
                                    }
                                }, getResources().getString(R.string.map_direction_api_key),
                                        ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.colorPrimary, null)).execute();

                                //mMap.addPolyline(gd.getPolyline(doc, 5,ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null)));
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.store_to_far_map, Toast.LENGTH_SHORT).show();
                    }

                    gd.setLogging(true);
                    gd.request(myPosition, destinationPosition, GoogleDirection.MODE_DRIVING);

                } catch (Exception e) {
                    if (APP_DEBUG)
                        e.printStackTrace();
                }

            }


            try {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        if (ServiceHandler.isNetworkAvailable(getApplicationContext())) {
                            gd.setCameraUpdateSpeed(10);
                        }
                    }
                };
                timer = new Timer();
                timer.scheduleAtFixedRate(task, 0, 6000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    public void initToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);

        APP_TITLE_VIEW.setText("Map");
        APP_DESC_VIEW.setVisibility(View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void moveToPosition(GoogleMap gm, LatLng targetPosition) {

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(targetPosition, 16));
        gm.getUiSettings().setZoomControlsEnabled(true);
        gm.addMarker(new MarkerOptions()
                .title(getString(R.string.your_destination))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .position(targetPosition));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        /*if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        destinationPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            if (APP_DEBUG) {
                Log.e("GooglePlayServices", "Available");
            }
            System.gc();
            mMap.clear();
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationPosition, 16));
*/

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void addCustomMarker(LatLng position, GoogleMap gm) {


        gm.clear();
        Marker marker = null;
        marker = gm.addMarker(

                MapsUtils.generateMarker(this,
                        null,
                        position,
                        null,
                        null
                ).draggable(false)

        );

        marker.setTag(1);
        MapsUtils.addMarker("1", marker);


    }
}
