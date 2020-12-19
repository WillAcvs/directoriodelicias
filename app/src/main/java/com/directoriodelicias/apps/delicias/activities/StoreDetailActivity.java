package com.directoriodelicias.apps.delicias.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.GPS.GPStracker;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.animation.ImageLoaderAnimation;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.classes.Discussion;
import com.directoriodelicias.apps.delicias.classes.OpeningTime;
import com.directoriodelicias.apps.delicias.classes.Store;
import com.directoriodelicias.apps.delicias.classes.User;
import com.directoriodelicias.apps.delicias.controllers.CampagneController;
import com.directoriodelicias.apps.delicias.controllers.SettingsController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.stores.StoreController;
import com.directoriodelicias.apps.delicias.customView.GalleryStoreCustomView;
import com.directoriodelicias.apps.delicias.customView.OfferCustomView;
import com.directoriodelicias.apps.delicias.fragments.SlideshowDialogFragment;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.StoreParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.unbescape.html.HtmlEscape;
import com.directoriodelicias.apps.delicias.utils.DateUtils;
import com.directoriodelicias.apps.delicias.utils.NSLog;
import com.directoriodelicias.apps.delicias.utils.TextUtils;
import com.directoriodelicias.apps.delicias.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;
import static com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController.isLogged;


public class StoreDetailActivity extends SimpleActivity implements
        GoogleMap.OnMapLoadedCallback, OnMapReadyCallback, View.OnClickListener {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.badge_category)
    TextView badge_category;
    @BindView(R.id.badge_closed)
    TextView badge_closed;
    @BindView(R.id.badge_open)
    TextView badge_open;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.opening_time_container)
    LinearLayout opening_time_container;
    @BindView(R.id.opening_time_content)
    TextView opening_time_content;

    @BindView(R.id.images_layout)
    RelativeLayout images_layout;
    @BindView(R.id.nbrPictures)
    TextView nbrPictures;

    @BindView(R.id.review_layout)
    RelativeLayout review_layout;
    @BindView(R.id.review_rate)
    TextView review_rate;
    @BindView(R.id.review_comment)
    TextView review_comment;

    @BindView(R.id.distance_layout)
    RelativeLayout distanceLayout;
    @BindView(R.id.distance_title)
    TextView distance_title;
    @BindView(R.id.distanceValue)
    TextView distanceValue;

    @BindView(R.id.adsLayout)
    LinearLayout adsLayout;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.btn_chat)
    ImageButton btn_chat_customer;

    @BindView(R.id.btn_share)
    ImageButton btn_share;
    @BindView(R.id.btn_phone)
    ImageButton phoneBtn;
    @BindView(R.id.description_content)
    TextView description_content;
    @BindView(R.id.header_title)
    TextView header_title;
    @BindView(R.id.header_subtitle)
    TextView header_subtitle;
    @BindView(R.id.progressMapLL)
    LinearLayout progressMapLL;


    private Context context;
    private ViewManager mViewManager;
    private Store storeData;
    private LatLng customerPosition;
    private GoogleMap mMap;
    private GPStracker mGPS;
    private Menu mMenu;
    private BottomSheetDialog mBottomSheetDialog;

    //custom view
    private OfferCustomView horizontalOfferList;
    private GalleryStoreCustomView horizontalGalleryList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.reviewed_activity_detail_store);

        //Bind views
        ButterKnife.bind(this);
        //contents of menu have changed, and menu should be redrawn.
        invalidateOptionsMenu();
        initParams();

        setupViews();
        //setup view manager showError loading content
        setupViewManager();

        //handle button click event
        handleButtonClickEvent();

        //setup the ADMOB
        setupAdmob();

        //campaign handler
        try {
            int cid = Integer.parseInt(getIntent().getExtras().getString("cid"));
            CampagneController.markView(cid);
        } catch (Exception e) {

        }

        //listing store data
        listingStoreData();


        //button click listener
        btn_chat_customer.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        phoneBtn.setOnClickListener(this);

    }

    private void listingStoreData() {

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getStore();
            }
        }, 500);

    }


    private void initParams() {
        //Set current context
        context = this;
        mGPS = new GPStracker(context);

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


    private void setupViews() {

        //setup toolbar
        setupToolbar(toolbar);
        getAppBarSubtitle().setVisibility(View.GONE);

        //setup scroll with header
        setupScrollNHeader(
                scrollView,
                findViewById(R.id.header_detail),
                SimpleHeaderSize.FULL,
                findViewById(R.id.store_detail)
        );


        //setup header views
        setupHeader();
    }


    private void setupHeader() {

        //setup all badge
        setupBadges();

    }

    private void setupBadges() {


        Drawable badge_closed_background = badge_closed.getBackground();
        if (badge_closed_background instanceof ShapeDrawable) {
            ((ShapeDrawable) badge_closed_background).getPaint().setColor(ContextCompat.getColor(this, R.color.orange_600));
        } else if (badge_closed_background instanceof GradientDrawable) {
            ((GradientDrawable) badge_closed_background).setColor(ContextCompat.getColor(this, R.color.orange_600));
        } else if (badge_closed_background instanceof ColorDrawable) {
            ((ColorDrawable) badge_closed_background).setColor(ContextCompat.getColor(this, R.color.orange_600));
        }

        Drawable badge_open_background = badge_open.getBackground();
        if (badge_closed_background instanceof ShapeDrawable) {
            ((ShapeDrawable) badge_open_background).getPaint().setColor(ContextCompat.getColor(this, R.color.seaGreen));
        } else if (badge_closed_background instanceof GradientDrawable) {
            ((GradientDrawable) badge_open_background).setColor(ContextCompat.getColor(this, R.color.seaGreen));
        } else if (badge_closed_background instanceof ColorDrawable) {
            ((ColorDrawable) badge_open_background).setColor(ContextCompat.getColor(this, R.color.seaGreen));
        }

    }


    private void updateCategoryBadge(String title) {
        updateCategoryBadge(title, null);
    }

    private void updateCategoryBadge(String title, String color_hex) {

        int color = ContextCompat.getColor(this, R.color.colorPrimary);

        if (color_hex != null && !color_hex.equals("null"))
            color = Color.parseColor(color_hex);

        Drawable badge_cat_background = badge_category.getBackground();
        if (badge_cat_background instanceof ShapeDrawable) {
            ((ShapeDrawable) badge_cat_background).getPaint().setColor(color);
        } else if (badge_cat_background instanceof GradientDrawable) {
            ((GradientDrawable) badge_cat_background).setColor(color);
        } else if (badge_cat_background instanceof ColorDrawable) {
            ((ColorDrawable) badge_cat_background).setColor(color);
        }

        badge_category.setText(title);

    }


    private void setupAdmob() {
        if (AppConfig.SHOW_ADS && AppConfig.SHOW_ADS_IN_STORE) {

            mAdView = findViewById(R.id.adView);
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        mMenu = menu;
        /////////////////////////////
        menu.findItem(R.id.bookmarks_icon).setVisible(true);
        /////////////////////////////
        menu.findItem(R.id.share_post).setVisible(true);
        Drawable send_location = new IconicsDrawable(this)
                .icon(CommunityMaterial.Icon.cmd_share_variant)
                .color(ResourcesCompat.getColor(getResources(), R.color.white, null))
                .sizeDp(22);
        menu.findItem(R.id.share_post).setIcon(send_location);
        /////////////////////////////


        return true;
    }

    private void initOfferRV(int store_id) {
        Map<String, Object> optionalParams = new HashMap<>();
        optionalParams.put("store_id", String.valueOf(store_id));
        horizontalOfferList = findViewById(R.id.horizontalOfferList);
        if (storeData.getLastOffer().equals("")) {
            horizontalOfferList.setVisibility(View.GONE);
        } else {
            horizontalOfferList.setVisibility(View.VISIBLE);
            horizontalOfferList.loadData(false, optionalParams);
            findViewById(R.id.card_show_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StoreDetailActivity.this, OffersListActivity.class));
                    overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);
                }
            });
        }

    }


    private void initGalleryRV(int store_id, int galleryCount) {
        horizontalGalleryList = findViewById(R.id.horizontalGalleryList);
        if (galleryCount > 0) {
            horizontalGalleryList.setVisibility(View.VISIBLE);
            horizontalGalleryList.loadData(store_id, "store", this, false);

        } else {
            horizontalGalleryList.setVisibility(View.GONE);

        }
    }


    private void getStore() {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        int store_id = 0;


        //get it from external url (deep linking)
        try {

            Intent appLinkIntent = getIntent();
            String appLinkAction = appLinkIntent.getAction();
            Uri appLinkData = appLinkIntent.getData();

            if (appLinkAction.equals(Intent.ACTION_VIEW)) {

                if (AppConfig.APP_DEBUG)
                    Toast.makeText(getApplicationContext(), appLinkData.toString(), Toast.LENGTH_LONG).show();
                store_id = Utils.dp_get_id_from_url(appLinkData.toString(), "store");

                if (AppConfig.APP_DEBUG)
                    Toast.makeText(getApplicationContext(), "The ID: " + store_id + " " + appLinkAction, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }


        //get it from internal app
        if (store_id == 0) {

            Bundle bundle = getIntent().getExtras();
            store_id = bundle.getInt("id");
            try {
                if (store_id == 0) {
                    store_id = Integer.parseInt(bundle.getString("id"));
                }
            } catch (Exception e) {
                store_id = 0;
            }
        }


        if (AppConfig.APP_DEBUG)
            Log.e("_2_store_id", String.valueOf(store_id));

        final Store mStore = StoreController.getStore(store_id);

        //GET DATA FROM API IF NETWORK IS AVAILABLE
        if (ServiceHandler.isNetworkAvailable(this)) {
            syncStore(store_id);
        } else {
            //IF NOT GET THE ITEM FROM THE DATABASE
            if (mStore != null && mStore.isLoaded() && mStore != null) {
                storeData = mStore;
                mViewManager.showContent();
                setupStoreDataViews();
            }
        }


        realm.commitTransaction();
    }

    private void setupStoreDataViews() {

        setBookmarkMenu();

        if (storeData.getGallery() > 0) {

            initGalleryRV(storeData.getId(), storeData.getGallery());
        }

        if (storeData.getListImages() != null && storeData.getListImages().size() > 0) {

            Glide.with(getBaseContext())
                    .load(storeData.getListImages().get(0)
                            .getUrlFull())
                    .fitCenter().placeholder(ImageLoaderAnimation.glideLoader(context))
                    .into(image);

        } else {

            Glide.with(getBaseContext())
                    .load(R.drawable.def_logo)
                    .centerCrop().placeholder(R.drawable.def_logo)
                    .into(image);

        }


        if (storeData.getCategory_color() != null)
            updateCategoryBadge(storeData.getCategory_name(), storeData.getCategory_color());
        else
            updateCategoryBadge(storeData.getCategory_name());

        /*
         * Opening time table
         */


        if (storeData.getOpening() == 1 || storeData.getOpening() == -1) {

            opening_time_container.setVisibility(View.VISIBLE);
            String opt_string = "";

            for (int i = 0; i < storeData.getOpening_time_table_list().size(); i++) {

                OpeningTime opt = storeData.getOpening_time_table_list().get(i);

                if (APP_DEBUG) {
                    Log.e("__getDay__", opt.toString());
                }

                String formatted_opening = DateUtils.getPrepareSimpleDate("01-01-2011 " + opt.getOpening(), "hh:mm a");
                String formatted_closing = DateUtils.getPrepareSimpleDate("01-01-2011 " + opt.getClosing(), "hh:mm a");

                String opening_status = "";

                String opening_day = opt.getDay().substring(0, 3);


                if (storeData.getOpening() == 1 && DateUtils.getCurrentDay().toLowerCase().equals(opening_day.toLowerCase())) {
                    opening_status = " \t - <b><font color=" + ContextCompat.getColor(this, R.color.seaGreen) + ">" + getString(R.string.open_now) + "</font><b>";
                } else if (storeData.getOpening() == -1 && DateUtils.getCurrentDay().toLowerCase().equals(opening_day.toLowerCase())) {
                    opening_status = "\t - <b><font color=\"red\">" + getString(R.string.closed) + "</font><b>";
                }

                //translate language
                if (opt.getDay() != null) {
                    switch (opt.getDay().toLowerCase()) {
                        case "monday":
                            opening_day = getString(R.string.monday);
                            break;
                        case "tuesday":
                            opening_day = getString(R.string.tuesday);
                            break;
                        case "wednesday":
                            opening_day = getString(R.string.wednesday);
                            break;
                        case "thursday":
                            opening_day = getString(R.string.thursday);
                            break;
                        case "friday":
                            opening_day = getString(R.string.friday);
                            break;
                        case "saturday":
                            opening_day = getString(R.string.saturday);
                            break;
                        case "sunday":
                            opening_day = getString(R.string.sunday);
                            break;

                    }

                }

                if (opt.getOpening().equals(opt.getClosing())) {
                    // opt_string = opt_string + "<b>" + TextUtils.capitalizeFirstLetter(opening_day) + "</b>: <font color=\"red\">N/A</font> " + opening_status + "<br>";
                    opt_string = opt_string + "<b>" + TextUtils.capitalizeFirstLetter(opening_day) + "</b>: \t - <i> " + opening_status + "</i> <br>";
                } else {
                    opt_string = opt_string + "<b>" + TextUtils.capitalizeFirstLetter(opening_day) + "</b>: \t - <i> " + formatted_opening + " - " + formatted_closing + " " + opening_status + "</i> <br>";
                }

            }

            opening_time_content.setText(Html.fromHtml(opt_string));

        } else {
            opening_time_container.setVisibility(View.GONE);

        }

        /*
         * End Opening time table
         */

        if (storeData.getOpening() == 0 || storeData.getOpening() == -1) {
            if (storeData.getOpening_time_table_list() != null && storeData.getOpening_time_table_list().size() > 0)
                badge_closed.setVisibility(View.VISIBLE);
            else
                badge_closed.setVisibility(View.GONE);

            badge_open.setVisibility(View.GONE);
        } else if (storeData.getOpening() == 1) {

            badge_closed.setVisibility(View.GONE);
            badge_open.setVisibility(View.VISIBLE);
        }


        float rated = (float) storeData.getVotes();
        DecimalFormat decim = new DecimalFormat("#.##");
        review_rate.setText(decim.format(rated));
        if (storeData.getNbr_votes() != null && !storeData.getNbr_votes().equals(""))
            review_comment.setText(
                    String.format(this.getString(R.string.reviews), storeData.getNbr_votes())
            );


        if (storeData.getPhone().trim().equals("")) {
            phoneBtn.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray_field, null));
            phoneBtn.setEnabled(false);
        }


        int nbrPics = storeData.getListImages() != null ? storeData.getListImages().size() : 1;
        nbrPictures.setText(nbrPics + "");

        if (mGPS.getLatitude() == 0 && mGPS.getLongitude() == 0) {
            distanceLayout.setVisibility(View.GONE);
        }

        if (storeData.getLatitude() != 0 && storeData.getLongitude() != 0
                && Utils.isNearMAXDistance(storeData.getDistance())) {
            distanceValue.setText(Utils.preparDistance(storeData.getDistance())
                    + " " +
                    Utils.getDistanceBy(storeData.getDistance()));
        } else if (!Utils.isNearMAXDistance(storeData.getDistance())) {
            distanceValue.setText("+100 KM");
        } else {
            distanceValue.setVisibility(View.GONE);
        }


        getAppBarTitle().setText(storeData.getName());
        header_title.setText(storeData.getName());
        header_subtitle.setText(storeData.getAddress());


        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                attachMap();
            }
        }, 2000);


        //make links in a TextView clickable
        description_content.setMovementMethod(LinkMovementMethod.getInstance());
        new StoreDetailActivity.decodeHtml().execute(storeData.getDetail());

        initOfferRV(storeData.getId());

        // ColorStateList.valueOf(Color.parseColor(storeData.getCategory_color()))

        if (storeData.getCanChat() == 1 && AppConfig.ENABLE_CHAT) {
            btn_chat_customer.setVisibility(View.VISIBLE);
        } else {
            btn_chat_customer.setVisibility(View.GONE);
        }


        mViewManager.showContent();


    }

    public void syncStore(final int store_id) {

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();

        mViewManager.showLoading();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_USER_GET_STORES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parse_store_data(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NSLog.e("ERROR", error.toString());
                mViewManager.showError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("limit", "1");
                params.put("store_id", String.valueOf(store_id));
                if (mGPS.canGetLocation()) {
                    params.put("latitude", mGPS.getLatitude() + "");
                    params.put("longitude", mGPS.getLongitude() + "");
                }
                //params.put("opening_time", 1 + "");

                NSLog.e("StoreDetailActivity", "  params :" + params.toString());

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }

    private void parse_store_data(String response) {

        try {

            NSLog.e("responseStoresString", response);

            JSONObject jsonObject = new JSONObject(response);

            final StoreParser mStoreParser = new StoreParser(jsonObject);
            RealmList<Store> list = mStoreParser.getStore();

            if (list.size() > 0) {

                StoreController.insertStores(list);
                storeData = list.get(0);
                setupStoreDataViews();

            } else {
                mViewManager.showEmpty();
            }

        } catch (JSONException e) {
            //send a rapport to support
            e.printStackTrace();
            mViewManager.showError();

        }
    }


    public void notificationAgreement(final int bookmark_id, final int user_id, final int notificationStatus) {

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();

        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_NOTIFICATIONS_AGREEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (AppConfig.APP_DEBUG) {
                        Log.e("notificationAgreement", "response  : " + response);
                    }

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {
                        Toast.makeText(StoreDetailActivity.this, "Notification agreement granted for this business ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StoreDetailActivity.this, "Something went wrong , please try later ", Toast.LENGTH_SHORT).show();
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

                if (AppConfig.APP_DEBUG) {
                    Log.e("notificationAgreement", "params :" + params.toString());
                }

                return params;
            }

        };


        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }

    private void attachMap() {

        try {

            SupportMapFragment mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapping);
            if (mSupportMapFragment == null) {
                androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                mSupportMapFragment.setRetainInstance(true);
                fragmentTransaction.replace(R.id.mapping, mSupportMapFragment).commit();
            }
            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(this);
            }

        } catch (Exception e) {
            progressMapLL.setVisibility(View.GONE);
        }

    }

    @Override
    public void onMapLoaded() {
        Toast.makeText(getApplicationContext(), "Map is ready ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap = googleMap;
        if (storeData.getLatitude() != null && storeData.getLatitude() != null) {

            double TraderLat = storeData.getLatitude();
            double TraderLng = storeData.getLongitude();
            customerPosition = new LatLng(TraderLat, TraderLng);
            //move map
            moveToPosition(mMap, customerPosition);
        }

        progressMapLL.setVisibility(View.GONE);

    }

    private void moveToPosition(GoogleMap gm, LatLng targetPosition) {

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(targetPosition, 16));
        gm.getUiSettings().setZoomControlsEnabled(true);
        gm.addMarker(new MarkerOptions()
                .title(this.getString(R.string.your_destination))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .position(targetPosition));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_phone) {
            try {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + storeData.getPhone().trim()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    String[] permission = new String[]{Manifest.permission.CALL_PHONE};
                    SettingsController.requestPermissionM(StoreDetailActivity.this, permission);

                    return;
                }
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.store_call_error) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.btn_chat) {
            if (isLogged()) {

                int userId = 0;
                try {
                    userId = storeData.getUser().getId();
                } catch (Exception e) {
                    userId = storeData.getUser_id();
                }

                Intent intent = new Intent(StoreDetailActivity.this, MessengerActivity.class);
                intent.putExtra("type", Discussion.DISCUSION_WITH_USER);
                intent.putExtra("userId", userId);
                intent.putExtra("storeName", storeData.getName());
                startActivity(intent);

            } else {
                Intent intent = new Intent(StoreDetailActivity.this, CustomSearchActivity.LoginActivityV2.class);
                startActivity(intent);
                finish();
            }
        } else if (v.getId() == R.id.btn_share) {

            if (AppConfig.ENABLE_LOCAL_MAPS_DIRECTION) {
                if (storeData != null && mGPS.canGetLocation()) {

                    Intent intent = new Intent(StoreDetailActivity.this, MapDirectionActivity.class);
                    intent.putExtra("latitude", storeData.getLatitude() + "");
                    intent.putExtra("longitude", storeData.getLongitude() + "");
                    intent.putExtra("name", storeData.getName() + "");
                    intent.putExtra("description", storeData.getAddress() + "");
                    intent.putExtra("distance", storeData.getDistance() + "");

                    startActivity(intent);

                } else if (!mGPS.canGetLocation()) {
                    mGPS.showSettingsAlert();
                    Toast.makeText(StoreDetailActivity.this, R.string.enable_gps_map_direction, Toast.LENGTH_LONG).show();
                } else if (!ServiceHandler.isNetworkAvailable(context)) {
                    mGPS.showSettingsAlert();
                    Toast.makeText(StoreDetailActivity.this, R.string.enable_network_map_direction, Toast.LENGTH_LONG).show();
                }
            } else {
                Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", storeData.getLatitude(), storeData.getLongitude()));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (android.R.id.home == item.getItemId()) {
            finish();
        } else if (item.getItemId() == R.id.share_post) {

            double lat = storeData.getLatitude();
            double lng = storeData.getLongitude();
            String mapLink = null;

            try {
                //https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393
                mapLink = "https://maps.google.com/?q=" + URLEncoder.encode(storeData.getAddress(), "UTF-8") + "&ll=" + String.format("%f,%f", lat, lng);
            } catch (UnsupportedEncodingException e) {
                mapLink = "https://maps.google.com/?ll=" + String.format("%f,%f", lat, lng);
                e.printStackTrace();
            }


            @SuppressLint({"StringFormatInvalid", "LocalSuppress", "StringFormatMatches"}) String shared_text =
                    String.format(getString(R.string.shared_text),
                            storeData.getName(),
                            getString(R.string.app_name),
                            storeData.getLink()
                    );

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shared_text);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (item.getItemId() == R.id.bookmarks_icon) {
            if (isLogged()) {

                User currentUser = SessionsController.getSession().getUser();

                if (storeData.getSaved() > 0) {
                    removeStoreToBookmarks(this, currentUser.getId(), storeData.getId());
                } else {
                    saveStoreToBookmarks(this, currentUser.getId(), storeData.getId());
                }


            } else {
                startActivity(new Intent(StoreDetailActivity.this, CustomSearchActivity.LoginActivityV2.class));
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void setBookmarkMenu() {
        MenuItem bookmarksItemMenu = mMenu.findItem(R.id.bookmarks_icon);
        if (bookmarksItemMenu != null) {
            if ((SessionsController.isLogged() && storeData.getSaved() > 0)) {
                Drawable cmd_bookmark = new IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_bookmark)
                        .color(ResourcesCompat.getColor(getResources(), R.color.white, null))
                        .sizeDp(18);
                bookmarksItemMenu.setIcon(cmd_bookmark);
            } else {
                Drawable cmd_bookmark = new IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_bookmark_outline)
                        .color(ResourcesCompat.getColor(getResources(), R.color.white, null))
                        .sizeDp(18);
                bookmarksItemMenu.setIcon(cmd_bookmark);
            }
        }

    }

    private void handleButtonClickEvent() {
        distanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppConfig.ENABLE_LOCAL_MAPS_DIRECTION) {
                    if (storeData != null && mGPS.canGetLocation()) {

                        Intent intent = new Intent(StoreDetailActivity.this, MapDirectionActivity.class);
                        intent.putExtra("latitude", storeData.getLatitude() + "");
                        intent.putExtra("longitude", storeData.getLongitude() + "");
                        intent.putExtra("name", storeData.getName() + "");
                        intent.putExtra("description", storeData.getAddress() + "");
                        intent.putExtra("distance", storeData.getDistance() + "");

                        startActivity(intent);

                    } else if (!mGPS.canGetLocation()) {
                        mGPS.showSettingsAlert();
                        Toast.makeText(StoreDetailActivity.this, R.string.enable_gps_map_direction, Toast.LENGTH_LONG).show();
                    } else if (!ServiceHandler.isNetworkAvailable(context)) {
                        mGPS.showSettingsAlert();
                        Toast.makeText(StoreDetailActivity.this, R.string.enable_network_map_direction, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", storeData.getLatitude(), storeData.getLongitude()));

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }

            }
        });

        review_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewsActivity.class);
                intent.putExtra("store_id", storeData.getId());
                context.startActivity(intent);
            }
        });

        images_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storeData != null && storeData.getListImages().size() > 0) {
                    SlideshowDialogFragment.newInstance().show(StoreDetailActivity.this, storeData.getListImages(), 0);
                }
            }
        });


    }

    private void showBottomSheetDialog(final int bookmark_id) {

        final View view = getLayoutInflater().inflate(R.layout.notifyme_sheet, null);
        ((TextView) view.findViewById(R.id.name)).setText("Do you want to receive notification ?");
        ((TextView) view.findViewById(R.id.address)).setText("By clicking on Notify me, you will receive notification from this business ");
        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        (view.findViewById(R.id.bt_details)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationAgreement(bookmark_id, SessionsController.getSession().getUser().getId(), 1);
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

    public void saveStoreToBookmarks(final Context context, final int user_id, final int int_id) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_SAVE_STORE_BOOKMARK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (AppConfig.APP_DEBUG) {
                    Log.e("response", response);
                }

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {

                        storeData = StoreController.doSave(storeData.getId(), 1);
                        if (storeData != null) {
                            setBookmarkMenu();
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
                params.put("store_id", String.valueOf(int_id));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    public void removeStoreToBookmarks(final Context context, final int user_id, final int int_id) {

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SimpleRequest request = new SimpleRequest(Request.Method.POST,
                Constances.API.API_REMOVE_STORE_BOOKMARK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (AppConfig.APP_DEBUG) {
                    Log.e("response", response);
                }

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt(Tags.SUCCESS) == 1) {
                        storeData = StoreController.doSave(storeData.getId(), 0);
                        if (storeData != null) {
                            setBookmarkMenu();
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
                params.put("store_id", String.valueOf(int_id));

                return params;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    private class decodeHtml extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(final String text) {
            super.onPostExecute(text);
            description_content.setText(Html.fromHtml(text));
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        storeData.setDetail(text);
                        realm.copyToRealmOrUpdate(storeData);
                    } catch (Exception e) {

                    }

                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            return HtmlEscape.unescapeHtml(params[0]);
        }
    }


}
