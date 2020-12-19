package com.directoriodelicias.apps.delicias.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.GPS.GoogleDirection;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Event;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.events.EventController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.customView.StoreCardCustomView;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.EventParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.DateUtils;
import com.directoriodelicias.apps.delicias.utils.TextUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.wuadam.awesomewebview.AwesomeWebView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;
import static com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController.getSession;
import static com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController.isLogged;


public class EventDetailActivity extends SimpleActivity implements ViewManager.CustomView, OnMapReadyCallback, View.OnClickListener {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.customStoreCV)
    StoreCardCustomView customStoreCV;
    @BindView(R.id.description_content)
    TextView description;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.event_date_begin)
    TextView event_date_begin;
    @BindView(R.id.event_begin_date_end)
    TextView event_begin_date_end;
    @BindView(R.id.day_calendar)
    TextView event_day;
    @BindView(R.id.month_calendar)
    TextView event_month;

    @BindView(R.id.header_title)
    TextView header_title;


    @BindView(R.id.progressMapLL)
    LinearLayout progressMapLL;

    @BindView(R.id.joinBtn)
    Button joinBtn;
    @BindView(R.id.unjoinBtn)
    Button unjoinBtn;
    @BindView(R.id.phoneBtn)
    Button phoneBtn;
    @BindView(R.id.webBtn)
    Button webBtn;
    @BindView(R.id.maps_container)
    LinearLayout mapcontainer;
    @BindView(R.id.mapping)
    View mapping;

    @BindView(R.id.adsLayout)
    LinearLayout adsLayout;
    @BindView(R.id.adView)
    AdView mAdView;


    private Context context;
    private ViewManager mViewManager;
    private GoogleMap mMap;
    private GoogleDirection gd;
    private Event eventData;
    private BottomSheetDialog mBottomSheetDialog;


    private LatLng customerPosition, myPosition;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.reviewed_activity_detail_event);
        ButterKnife.bind(this);

        //contents of menu have changed, and menu should be redrawn.
        invalidateOptionsMenu();

        //setup toolbar and header
        setupViews();

        //setup view manager showError loading content
        setupViewManager();

        //populating data
        listingEventData();

    }


    protected void listingEventData() {

        int eid = 0;

        try {
            eid = getIntent().getExtras().getInt("id");
            //get it from external url (deep linking)
            if (eid == 0) {

                Intent appLinkIntent = getIntent();
                String appLinkAction = appLinkIntent.getAction();
                Uri appLinkData = appLinkIntent.getData();

                if (appLinkAction.equals(Intent.ACTION_VIEW)) {

                    if (AppConfig.APP_DEBUG)
                        Toast.makeText(getApplicationContext(), appLinkData.toString(), Toast.LENGTH_LONG).show();

                    eid = Utils.dp_get_id_from_url(appLinkData.toString(), "event");

                    if (AppConfig.APP_DEBUG)
                        Toast.makeText(getApplicationContext(), "The ID: " + eid + " " + appLinkAction, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //get it from external url (deep linking)


        eventData = EventController.getEvent(eid);

        //GET DATA FROM API IF NETWORK IS AVAILABLE
        if (ServiceHandler.isNetworkAvailable(this)) {
            syncEvent(eid);
        } else {
            //IF NOT GET THE ITEM FROM THE DATABASE
            if (eventData != null) {
                putDataIntoViews();
            }
        }


    }

    public void syncEvent(final int evnt_id) {

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();

        mViewManager.showLoading();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.e("responseStoresString", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);

                    //Log.e("response",response);

                    final EventParser mEventParser = new EventParser(jsonObject);
                    RealmList<Event> list = mEventParser.getEvents();

                    if (list.size() > 0) {
                        EventController.insertEvents(list);
                        eventData = list.get(0);
                        putDataIntoViews();

                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                    mViewManager.showError();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }
                mViewManager.showError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //
                params.put("limit", "1");
                params.put("event_id", String.valueOf(evnt_id));


                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


    private void setupViews() {

        //setup toolbar
        setupToolbar(toolbar);
        getAppBarSubtitle().setVisibility(View.GONE);

        //setup scroll with header
        setupScrollNHeader(
                scrollView,
                findViewById(R.id.header_detail),
                SimpleHeaderSize.FULL,
                findViewById(R.id.event_detail)
        );


        //setup header views
        setupHeader();

    }


    private void initParams() {
        //Set current context
        context = this;

    }

    private void setupViewManager() {

        //setup view manager
        mViewManager = new ViewManager(this);
        mViewManager.setLoadingView(findViewById(R.id.loading));
        mViewManager.setContentView(findViewById(R.id.content));
        mViewManager.setErrorView(findViewById(R.id.error));
        mViewManager.setEmptyView(findViewById(R.id.empty));

        mViewManager.setListener(new ViewManager.CallViewListener() {
            @Override
            public void onContentShown() {
                scrollView.setNestedScrollingEnabled(true);
            }

            @Override
            public void onErrorShown() {

            }

            @Override
            public void onEmptyShown() {
                scrollView.setNestedScrollingEnabled(false);
            }

            @Override
            public void onLoadingShown() {

            }
        });

        mViewManager.showLoading();

    }

    private void setupScrollView() {


    }

    private void setupHeader() {


    }


    @Override
    protected void onResume() {

        if (mAdView != null)
            mAdView.resume();

        super.onResume();
    }

    @Override
    protected void onPause() {

        if (mAdView != null)
            mAdView.pause();

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mAdView != null)
            mAdView.destroy();

        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        menu.findItem(R.id.share_post).setVisible(true);


        /////////////////////////////
        menu.findItem(R.id.share_post).setVisible(true);
        Drawable send_location = new IconicsDrawable(this)
                .icon(CommunityMaterial.Icon.cmd_share_variant)
                .color(ResourcesCompat.getColor(getResources(), R.color.defaultColor, null))
                .sizeDp(22);
        menu.findItem(R.id.share_post).setIcon(send_location);
        /////////////////////////////


        return true;
    }


    @Override
    public void customErrorView(View v) {

    }

    @Override
    public void customLoadingView(View v) {

    }

    @Override
    public void customEmptyView(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            if (!MainActivity.isOpend()) {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        } else if (item.getItemId() == R.id.share_post) {

            double lat = eventData.getLat();
            double lng = eventData.getLng();
            String mapLink = null;

            try {
                //https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393
                mapLink = "https://maps.google.com/?q=" + URLEncoder.encode(eventData.getAddress(), "UTF-8") + "&ll=" + String.format("%f,%f", lat, lng);
            } catch (UnsupportedEncodingException e) {
                mapLink = "https://maps.google.com/?ll=" + String.format("%f,%f", lat, lng);
                e.printStackTrace();
            }


            @SuppressLint({"StringFormatInvalid", "LocalSuppress", "StringFormatMatches"}) String shared_text =
                    String.format(getString(R.string.shared_text),
                            eventData.getName(),
                            getString(R.string.app_name),
                            eventData.getLink()
                    );

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shared_text);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void putDataIntoViews() {

        getAppBarTitle().setText((!eventData.getName().equals("") && eventData.getName().length() > 20) ? (eventData.getName().substring(0, 20) + "...") : eventData.getName());

        header_title.setText(eventData.getName());

        //description.setText(eventData.getAddress());
        description.setText(
                Html.fromHtml(/*HtmlEscape.unescapeHtml(*/eventData.getDescription()/*)*/)
        );

        new TextUtils.decodeHtml(description).execute(eventData.getDescription());


        event_date_begin.setText(DateUtils.getDateByTimeZone(eventData.getDateB(), "dd MMM yyyy"));
        event_begin_date_end.setText(DateUtils.getDateByTimeZone(eventData.getDateE(), "dd MMM yyyy"));


        Drawable icon_address_map_marker = new IconicsDrawable(this)
                .icon(CommunityMaterial.Icon.cmd_map_marker)
                .color(ResourcesCompat.getColor(getResources(), R.color.white, null))
                .sizeDp(18);
        if (eventData.getStore_id() > 0 && !eventData.getStore_name().equals("") && !eventData.getStore_name().toLowerCase()
                .equals("null")) {
            address.setText(eventData.getStore_name());
        } else {
            address.setText(eventData.getAddress());
        }
        address.setCompoundDrawables(icon_address_map_marker, null, null, null);
        address.setCompoundDrawablePadding(20);


        setupQuickButtons();


        if (APP_DEBUG) {
            Log.e("eventStore", eventData.getStore_id() + " - " + eventData.getStore_name());
        }

        if (eventData.getStore_id() > 0 && !eventData.getStore_name().equals("") && !eventData.getStore_name().toLowerCase()
                .equals("null")) {
            customStoreCV.loadData(eventData.getStore_id(), false, new StoreCardCustomView.StoreListener() {
                @Override
                public void onLoaded(Store object) {
                    customStoreCV.setVisibility(View.VISIBLE);
                }
            });

        } else {
            customStoreCV.setVisibility(View.GONE);
        }

        event_day.setText(DateUtils.getDateByTimeZone(eventData.getDateB(), "dd"));
        event_month.setText(DateUtils.getDateByTimeZone(eventData.getDateB(), "MMM"));


        initMapping();

        mViewManager.showContent();

    }

    private void setupQuickButtons() {

        if (eventData.getTel().length() > 0 && !eventData.getTel().equals("")) {
            phoneBtn.setEnabled(true);
            phoneBtn.setOnClickListener(this);
        } else {
            phoneBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray_field, null));
            phoneBtn.setEnabled(false);
        }

        if (eventData.getListImages().size() > 0) {

            Glide.with(getBaseContext())
                    .load(eventData.getListImages().get(0).getUrl500_500())
                    .centerCrop()
                    .placeholder(ImageLoaderAnimation.glideLoader(this))
                    .into(image);
        } else {

            Glide.with(getBaseContext())
                    .load(R.drawable.def_logo)
                    .centerCrop().placeholder(R.drawable.def_logo)
                    .into(image);
        }


        if (eventData.getWebSite().length() > 0 && !eventData.getWebSite().equalsIgnoreCase("null")) {
            webBtn.setVisibility(View.VISIBLE);

            try {

                /*website.setText(eventData.getWebSite());
                website.setPaintFlags(website.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);*/

                webBtn.setOnClickListener(this);

            } catch (Exception e) {
                if (APP_DEBUG) {
                    Log.e("WebView", e.getMessage());
                }
            }

        } else {
            webBtn.setVisibility(View.GONE);
        }

        initializeBtn();
        joinBtn.setOnClickListener(this);
        unjoinBtn.setOnClickListener(this);


    }


    private void attachMap() {

        try {

            SupportMapFragment mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapping);
            if (mSupportMapFragment == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                mSupportMapFragment.setRetainInstance(true);
                fragmentTransaction.replace(R.id.mapping, mSupportMapFragment).commit();
            }
            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(this);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        putMap();

        progressMapLL.setVisibility(View.GONE);

    }


    private void initMapping() {

        if (eventData.getLat() != null && eventData.getLng() != null) {

            double TraderLat = eventData.getLat();
            double TraderLng = eventData.getLng();

            customerPosition = new LatLng(TraderLat, TraderLng);
            customerPosition = new LatLng(TraderLat, TraderLng);
            //INITIALIZE MY LOCATION
            GPStracker trackMe = new GPStracker(this);

            myPosition = new LatLng(trackMe.getLatitude(), trackMe.getLongitude());

            attachMap();

        } else {

            if (AppConfig.APP_DEBUG) {
                Toast.makeText(this, "Debug mode: Couldn't get position maps", Toast.LENGTH_LONG).show();
            }

            mapcontainer.setVisibility(View.GONE);
        }

    }

    private void putMap() {

        if (mMap != null) {

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customerPosition, 16));
            //trader location

            Bitmap b = ((BitmapDrawable) Utils.changeDrawableIconMap(
                    this, R.drawable.ic_marker)).getBitmap();
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(b);


            mMap.addMarker(new MarkerOptions().position(customerPosition)
                    .title(eventData.getName())
                    .anchor(0.0f, 1.0f)
                    .icon(icon)
                    .snippet(eventData.getAddress())).showInfoWindow();

            mMap.addMarker(new MarkerOptions().position(myPosition)
                    .title(eventData.getName())
                    .anchor(0.0f, 1.0f)
                    .draggable(true)
                    //.icon(icon)
                    .snippet(eventData.getAddress())).showInfoWindow();

            if (ServiceHandler.isNetworkAvailable(this)) {
                try {
                    gd = new GoogleDirection(this);
                    //My Location
                    gd.setOnDirectionResponseListener(new GoogleDirection.OnDirectionResponseListener() {

                        @Override
                        public void onResponse(String status, Document doc, GoogleDirection gd) {
                            mMap.addPolyline(gd.getPolyline(doc, 8,
                                    ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
                            //mMap.setMyLocationEnabled(true);
                        }
                    });
                    gd.setLogging(true);
                    gd.request(myPosition, customerPosition, GoogleDirection.MODE_DRIVING);

                } catch (Exception e) {
                    if (APP_DEBUG)
                        e.printStackTrace();
                }

            }


        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.phoneBtn) {
            try {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + eventData.getTel().trim()));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.store_call_error) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.webBtn) {
            new AwesomeWebView.Builder(EventDetailActivity.this)
                    .statusBarColorRes(R.color.colorAccent)
                    .theme(R.style.FinestWebViewAppTheme)
                    .show(eventData.getWebSite());
        } else if (v.getId() == R.id.joinBtn) {
            // Click action
            if (eventData != null) {
                if (isLogged()) {

                    User currentUser = SessionsController.getSession().getUser();
                    saveEventToBookmarks(EventDetailActivity.this, currentUser.getId(), eventData.getId());

                } else {
                    startActivity(new Intent(EventDetailActivity.this, CustomSearchActivity.LoginActivityV2.class));
                    overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
                }
            }
        } else if (v.getId() == R.id.unjoinBtn) {
            // Click action
            if (eventData != null) {
                if (isLogged()) {
                    User currentUser = SessionsController.getSession().getUser();
                    if (eventData.getSaved() > 0) {
                        removeEventToBookmarks(EventDetailActivity.this, currentUser.getId(), eventData.getId());
                    }
                }

            }

        }
    }


    private void initializeBtn() {

        if (SessionsController.isLogged() && eventData.getSaved() > 0) {
            joinBtn.setVisibility(View.GONE);
            unjoinBtn.setVisibility(View.VISIBLE);
        } else {
            joinBtn.setVisibility(View.VISIBLE);
            unjoinBtn.setVisibility(View.GONE);
        }
    }


    public void saveEventToBookmarks(final Context context, final int user_id, final int int_id) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_SAVE_EVENT_BOOKMARK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (AppConfig.APP_DEBUG) {
                    Log.e("response", response);
                }

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {

                        eventData = EventController.doSave(eventData.getId(), 1);
                        if (eventData != null) {
                            initializeBtn();
                        }

                        showBottomSheetDialog(jsonObject.getInt(Tags.RESULT));
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong , please try later ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", String.valueOf(user_id));
                params.put("event_id", String.valueOf(int_id));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    public void removeEventToBookmarks(final Context context, final int user_id, final int int_id) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_REMOVE_EVENT_BOOKMARK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (AppConfig.APP_DEBUG) {
                    Log.e("response", response);
                }

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {

                        eventData = EventController.doSave(eventData.getId(), 0);
                        if (eventData != null) {
                            initializeBtn();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong , please try later ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", String.valueOf(user_id));
                params.put("event_id", String.valueOf(int_id));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }


    private void showBottomSheetDialog(final int bookmark_id) {


        final View view = getLayoutInflater().inflate(R.layout.notifyme_sheet, null);
        ((TextView) view.findViewById(R.id.name)).setText(getString(R.string.remind_me));
        ((TextView) view.findViewById(R.id.address)).setText(getString(R.string.remind_me_msg));
        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        ((TextView) view.findViewById(R.id.name)).setText(getString(R.string.remind_me));
        (view.findViewById(R.id.bt_details)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationAgreement(bookmark_id, getSession().getUser().getId(), 1);
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }


    public void notificationAgreement(final int bookmark_id, final int user_id, final int notificationStatus) {

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();

        //mViewManager.showLoading();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_NOTIFICATIONS_AGREEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.e("notificationAgreement", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {
                        Toast.makeText(getApplicationContext(), "Notification agreement granted for this business ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong , please try later ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                    mViewManager.showError();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (AppConfig.APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }
                mViewManager.showError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("bookmark_id", String.valueOf(bookmark_id));
                params.put("user_id", String.valueOf(user_id));
                params.put("agreement", String.valueOf(notificationStatus)); //todo : set the agreement according to the store status

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }


    private void setupAdmob() {
        if (AppConfig.SHOW_ADS && AppConfig.SHOW_ADS_IN_EVENT) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("FFD811D6CAB26FA340E98A773B3408ED")
                    .addTestDevice("3CB74DFA141BF4D0823B8EA7D94531B5")
                    .build();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE);
            adsLayout.setVisibility(View.VISIBLE);

        } else
            adsLayout.setVisibility(View.GONE);
    }
}
