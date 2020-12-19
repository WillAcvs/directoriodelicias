package com.directoriodelicias.apps.delicias.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.Animation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.customView.OfferCustomView;
import com.directoriodelicias.apps.delicias.fragments.CustomSearchFragment;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.StoreParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.MapsUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;

public class MapStoresListActivity extends AppCompatActivity implements OnMapReadyCallback {

    public ViewManager mViewManager;
    public int INT_RESULT_VERSION = 1001;
    private GoogleMap mMap;
    private Context context;
    private LatLng myPosition;
    private int COUNT = 0;
    private Toolbar toolbar;
    //init request http
    private RequestQueue queue;
    private GPStracker mGPS;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;
    private LinearLayout content;
    private OfferCustomView horizentalOffersView;
    private LinearLayout storeOffersLayout;
    private HashMap<String, Object> searchParams;
    private String order_by_recent;
    private LatLng recentStoreLocation;
    private LinearLayout store_focus_layout;
    private boolean requestStarted = false;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);

        context = this;
        setupToolbar();

        mViewManager = new ViewManager(this);
        mViewManager.setLoadingView(findViewById(R.id.loading));
        mViewManager.setContentView(findViewById(R.id.content_my_store));
        mViewManager.setErrorView(findViewById(R.id.error));
        mViewManager.setEmptyView(findViewById(R.id.empty));
        mViewManager.showLoading();

        store_focus_layout = findViewById(R.id.store_focus_layout);
        storeOffersLayout = findViewById(R.id.store_offers_layout);

        content = findViewById(R.id.content_my_store);
        initStoreOffersFocusLayout();


        mGPS = new GPStracker(this);

        if (!mGPS.canGetLocation())
            mGPS.showSettingsAlert();


        queue = VolleySingleton.getInstance(this).getRequestQueue();


        //GET TREAD GEO POINT FROM INTENT


        //INITIALIZE MY LOCATION
        mGPS = new GPStracker(this);

        attachMap();

        myPosition = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());


        FloatingActionButton fab = content.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, context.getString(R.string.searching_for_stores_bearby), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                mGPS = new GPStracker(context);
                if (mGPS.canGetLocation()) {
                    if (mMap != null) {
                        LatLng po = new LatLng(
                                mMap.getCameraPosition().target.latitude,
                                mMap.getCameraPosition().target.longitude
                        );
                        float zoomLevel = (float) 12.0;
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(po, zoomLevel));

                        getStores(po, true);
                    }
                } else {
                    mGPS.showSettingsAlert();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void initStoreOffersFocusLayout() {

        store_focus_layout.setVisibility(View.GONE);
        storeOffersLayout.setVisibility(View.GONE);

    }

    private void hideStoreFocusLayout() {

        if (store_focus_layout.isShown())
            com.directoriodelicias.apps.delicias.animation
                    .Animation.hideWithZoomEffect(store_focus_layout);

    }

    private void showStoreFocusLayout(final Store store) {


        TextView title = store_focus_layout.findViewById(R.id.name);
        RatingBar rateBar = store_focus_layout.findViewById(R.id.ratingBar2);
        TextView rateNbr = store_focus_layout.findViewById(R.id.rate);

        title.setText(store.getName());
        rateBar.setRating((float) store.getVotes());

        float rated = (float) store.getVotes();
        DecimalFormat decim = new DecimalFormat("#.##");

        rateNbr.setText(decim.format(rated) + "  (" + store.getNbr_votes() + ")");


        store_focus_layout.setVisibility(View.VISIBLE);
        com.directoriodelicias.apps.delicias.animation.Animation.startCustomZoom(store_focus_layout);
        //give permission to hide this layout after 3 second


        store_focus_layout.findViewById(R.id.closeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideStoreFocusLayout();

            }
        });

        store_focus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideStoreFocusLayout();

                int id = store.getId();
                Intent intent = new Intent(MapStoresListActivity.this, StoreDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });


    }

    private void showOffersFocusLayout(final Store store) {

        if (store.getLastOffer().equals("")) {
            storeOffersLayout.setVisibility(View.GONE);
        } else {
            Map<String, Object> optionalParams = new HashMap<>();
            optionalParams.put("store_id", String.valueOf(store.getId()));
            horizentalOffersView = findViewById(R.id.horizontalOfferList);
            horizentalOffersView.loadData(false, optionalParams);
            storeOffersLayout.setVisibility(View.VISIBLE);

            Animation.startCustomZoom(storeOffersLayout);
            storeOffersLayout.findViewById(R.id.closeOfferLayoutBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (storeOffersLayout.isShown())
                        Animation.hideWithZoomEffect(storeOffersLayout);
                }
            });
        }


    }

    private void attachMap() {

        try {

            SupportMapFragment mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mSupportMapFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                mSupportMapFragment.setRetainInstance(true);
                fragmentTransaction.replace(R.id.mapping, mSupportMapFragment).commit();
            }
            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(MapStoresListActivity.this);
            }

        } catch (Exception e) {

        }

    }

    private void moveHandler() {

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (requestStarted) {

                    LatLng po = new LatLng(
                            mMap.getCameraPosition().target.latitude,
                            mMap.getCameraPosition().target.longitude
                    );

                    // getStores(po,true);
                }
            }
        }, 3000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        initMapping();

        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {

                if (AppConfig.APP_DEBUG)
                    Log.i("onCameraMoveCanceled", String.valueOf(mMap.getCameraPosition().target.latitude));
            }
        });


        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                if (AppConfig.APP_DEBUG)
                    Log.i("onCameraMove", String.valueOf(mMap.getCameraPosition().target.latitude));

                moveHandler();

            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                if (AppConfig.APP_DEBUG)
                    Log.i("onCameraMoveStarted", String.valueOf(i));

            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                if (AppConfig.APP_DEBUG)
                    Log.i("onCameraIdle", String.valueOf(mMap.getCameraPosition().target.latitude));
            }
        });


    }


    private void initMapping() {

        if (mMap != null) {

            mMap.getUiSettings().setZoomControlsEnabled(true);

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);


            mGPS = new GPStracker(getBaseContext());
            final LatLng po = new LatLng(mGPS.getLatitude(), mGPS.getLatitude());


            getStores(po, false);

        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private void getStores(final LatLng position, final boolean refresh) {

        requestStarted = true;

        if (refresh == false) {
            mViewManager.showLoading();
        }

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_STORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    requestStarted = false;
                    if (AppConfig.APP_DEBUG)
                        Log.i("____response", response);

                    JSONObject jsonObject = new JSONObject(response);
                    final StoreParser mStoreParser = new StoreParser(jsonObject);

                    COUNT = mStoreParser.getIntArg(Tags.COUNT);
                    int success = Integer.parseInt(mStoreParser.getStringAttr("success"));

                    if (success == 1) {

                        if (COUNT > 0) {

                            if (refresh == false) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));
                                mViewManager.showContent();
                            }

                            final List<Store> list = mStoreParser.getStore();

                            Bitmap b = ((BitmapDrawable) Utils.changeDrawableIconMap(
                                    MapStoresListActivity.this, R.drawable.ic_marker)).getBitmap();
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(b);

                            if (refresh == true) {
                                mMap.clear();
                            }


                            if (list.size() > 0) {
                                if (order_by_recent != null && order_by_recent.equals(Constances.OrderByFilter.RECENT)) {
                                    recentStoreLocation = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
                                    float zoomLevel = (float) 12.0;
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(recentStoreLocation, zoomLevel));
                                } else {
                                    float zoomLevel = (float) 12.0;
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomLevel));
                                }
                            }

                            for (int i = 0; i < list.size(); i++) {


                                String imageUrl = null;

                                if (list.get(i).getListImages() != null && list.get(i).getListImages().size() > 0) {
                                    imageUrl = list.get(i).getListImages().get(0).getUrl100_100();
                                }

                                if (imageUrl != null) {

                                    final int finalI = i;


                                    Glide.with(getApplicationContext())
                                            .asBitmap()
                                            .load(imageUrl)
                                            .into(new CustomTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                    String promo = null;
                                                    if (!list.get(finalI).getLastOffer().equals("")) {
                                                        promo = list.get(finalI).getLastOffer();
                                                    }

                                                    Marker marker = null;
                                                    marker = mMap.addMarker(

                                                            MapsUtils.generateMarker(MapStoresListActivity.this,
                                                                    String.valueOf(list.get(finalI).getId()),
                                                                    new LatLng(list.get(finalI).getLatitude(), list.get(finalI).getLongitude()
                                                                    ),
                                                                    resource,
                                                                    promo
                                                            ).draggable(false)

                                                    );

                                                    marker.setTag(finalI);
                                                    MapsUtils.addMarker(String.valueOf(list.get(finalI).getId()), marker);

                                                }

                                                @Override
                                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                                }
                                            });


                                } else {

                                    String promo = null;
                                    if (!list.get(i).getLastOffer().equals("")) {
                                        promo = list.get(i).getLastOffer();
                                    }

                                    Marker marker = null;
                                    marker = mMap.addMarker(

                                            MapsUtils.generateMarker(MapStoresListActivity.this,
                                                    String.valueOf(list.get(i).getId()),
                                                    new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude()
                                                    ),
                                                    null,
                                                    promo
                                            ).draggable(false)

                                    );

                                    marker.setTag(i);
                                    MapsUtils.addMarker(String.valueOf(list.get(i).getId()), marker);

                                }


                            }

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {

                                    int position = (int) (marker.getTag());

                                    if (AppConfig.APP_DEBUG)
                                        Toast.makeText(MapStoresListActivity.this, list.get(position).getName(),
                                                Toast.LENGTH_LONG).show();

//
                                    showStoreFocusLayout(list.get(position));
                                    showOffersFocusLayout(list.get(position));

                                    return false;
                                }
                            });

                        } else {
                            Toast.makeText(MapStoresListActivity.this, "No business found !! ", Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                    requestStarted = false;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

                //mViewManager.showError();
                requestStarted = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                if (mGPS.canGetLocation()) {
                    params.put("latitude", String.valueOf(position.latitude));
                    params.put("longitude", String.valueOf(position.longitude));
                }

                if (searchParams != null && !searchParams.isEmpty()) {
                    if (searchParams.containsKey("search") && searchParams.get("search") != null) {
                        params.put("search", (String) searchParams.get("search"));
                    }
                    if (searchParams.containsKey("category") && searchParams.get("category") != null) {
                        params.put("category_id", String.valueOf(searchParams.get("category")));
                    }
                    if (searchParams.containsKey("radius") && searchParams.get("radius") != null) {
                        int radius = (Integer) searchParams.get("radius");
                        params.put("radius", String.valueOf((radius * 1000)));
                    }
                    if (searchParams.containsKey("openingStatus") && searchParams.get("openingStatus") != null) {
                        int openingStatus = (Integer) searchParams.get("openingStatus");
                        if (openingStatus == 1)
                            params.put("opening_time", String.valueOf(openingStatus));
                    }
                    if (searchParams.containsKey("order_by") && searchParams.get("order_by") != null) {
                        params.put("order_by", (String) searchParams.get("order_by"));
                        order_by_recent = (String) searchParams.get("order_by");
                    }

                    if ((searchParams.containsKey("latitude") && searchParams.get("latitude") != null) &&
                            (searchParams.containsKey("longitude") && searchParams.get("longitude") != null)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            params.replace("latitude", String.valueOf(searchParams.get("latitude")));
                            params.put("longitude", String.valueOf(searchParams.get("longitude")));

                            myPosition = new LatLng((Double) searchParams.get("latitude"), (Double) searchParams.get("longitude"));
                        } else {
                            myPosition = new LatLng(mGPS.getLatitude(), mGPS.getLongitude());
                        }
                    }


                } else {
                    params.put("order_by", Constances.OrderByFilter.NEARBY);
                }

                params.put("limit", context.getResources().getString(R.string.NBR_STORES_MAX_GEO_MAPS));
                params.put("page", "1");

                if (APP_DEBUG) {
                    Log.i("mapsStoresActivity", "  params map :" + params.toString());
                }

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


    public void setupToolbar() {

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setSubtitle("E-shop");
        getSupportActionBar().setTitle("---");
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        APP_TITLE_VIEW = toolbar.findViewById(R.id.toolbar_title);
        APP_DESC_VIEW = toolbar.findViewById(R.id.toolbar_subtitle);

        APP_DESC_VIEW.setVisibility(View.GONE);


        APP_TITLE_VIEW.setText(getString(R.string.MapStores));

        APP_DESC_VIEW.setVisibility(View.GONE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.anim.righttoleft_enter, R.anim.righttoleft_exit);

        } else if (id == R.id.action_refresh) {

            mGPS = new GPStracker(context);
            if (mGPS.canGetLocation()) {
                if (mMap != null) {
                    LatLng po = new LatLng(
                            mMap.getCameraPosition().target.latitude,
                            mMap.getCameraPosition().target.longitude
                    );
                    getStores(po, true);
                }
            } else {
                mGPS.showSettingsAlert();
            }

        } else if (id == R.id.action_search) {

            CustomSearchFragment.previousPageClass = this.getClass();
            startActivityForResult(new Intent(this, CustomSearchActivity.class), INT_RESULT_VERSION);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (INT_RESULT_VERSION == requestCode && resultCode == Activity.RESULT_OK) {

            if (data != null && data.hasExtra("searchParams")) {
                searchParams = (HashMap<String, Object>) data.getSerializableExtra("searchParams");
            }

            getStores(myPosition, true);
        } else {
            Toast.makeText(this, "Something is wrong with the search detail , please try later", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


}
