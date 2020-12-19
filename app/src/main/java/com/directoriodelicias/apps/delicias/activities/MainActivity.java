package com.directoriodelicias.apps.delicias.activities;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.Services.BusMessage;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.controllers.SettingsController;
import com.directoriodelicias.apps.delicias.controllers.categories.CategoryController;
import com.directoriodelicias.apps.delicias.controllers.sessions.SessionsController;
import com.directoriodelicias.apps.delicias.controllers.users.UserController;
import com.directoriodelicias.apps.delicias.dtmessenger.MessengerHelper;
import com.directoriodelicias.apps.delicias.fragments.V2MainFragment;
import com.directoriodelicias.apps.delicias.load_manager.ViewManager;
import com.directoriodelicias.apps.delicias.navigationdrawer.NavigationDrawerFragment;
import com.directoriodelicias.apps.delicias.network.VolleySingleton;
import com.directoriodelicias.apps.delicias.network.api_request.SimpleRequest;
import com.directoriodelicias.apps.delicias.parser.api_parser.CategoryParser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.utils.Tools;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.APP_DEBUG;
import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.SHOW_ADS;
import static com.directoriodelicias.apps.delicias.appconfig.AppConfig.SHOW_INTERSTITIAL_ADS_IN_STARTUP;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, V2MainFragment.Listener {

    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final int REQUEST_CHECK_SETTINGS_MAIN = 0x2;
    public static int height = 0;
    public static int width = 0;
    public static Menu mainMenu;
    private static boolean opened = false;
    public ViewManager mViewManager;
    Toolbar toolbar;
    private ActionBar actionBar;
    private InterstitialAd mInterstitialAd;
    /**********************************************/

    //init request http
    private RequestQueue queue;
    private TextView APP_TITLE_VIEW = null;
    private TextView APP_DESC_VIEW = null;

    //SHARED ¨PREFERENCES
    private Tracker mTracker;

    public static boolean isOpend() {
        return opened;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /*public static void updateBadge(FragmentActivity activity) {

        try {

            if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
                ActionItemBadge.update(activity, mainMenu.findItem(R.id.item_samplebadge), CommunityMaterial.Icon.cmd_bell_ring_outline,
                        ActionItemBadge.BadgeStyles.RED,
                        MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
            } else {
                ActionItemBadge.hide(mainMenu.findItem(R.id.item_samplebadge));
            }

        } catch (Exception e) {

        }

    }*/

    public static boolean isAppInForeground(Context context) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();

            return foregroundTaskPackageName.toLowerCase().equals(context.getPackageName().toLowerCase());
        } else {
            ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(appProcessInfo);
            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
                return true;
            }

            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            // App is foreground, but screen is locked, so show notification
            return km.inKeyguardRestrictedInputMode();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        opened = false;
    }

    /************   EVENT ALERT *******************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Init butterKnife
        //ButterKnife.bind(this);

        AppController application = (AppController) getApplication();
        mTracker = application.getDefaultTracker();


        //initialize the Google Mobile Ads SDK at app launch
        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_app_id));

        Display display = getWindowManager().getDefaultDisplay();
        width = getScreenWidth();
        height = display.getHeight();

        //Initialize web service API
        queue = VolleySingleton.getInstance(this).getRequestQueue();

        mViewManager = new ViewManager(this);
        mViewManager.setLoadingView(findViewById(R.id.loading));
        mViewManager.setContentView(findViewById(R.id.content_my_store));
        mViewManager.setErrorView(findViewById(R.id.error));
        mViewManager.setEmptyView(findViewById(R.id.empty));
        mViewManager.showContent();

        setupToolbar();


        int size = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        switch (size) {

            case Configuration.SCREENLAYOUT_SIZE_XLARGE:


                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                NavigationDrawerFragment fragNDF = new NavigationDrawerFragment();

                FragmentTransaction transactionNDF = getSupportFragmentManager().beginTransaction();
                //transaction.setCustomAnimations(R.animator.fade_in_listoffres, R.animator.fade_out_listoffres);

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transactionNDF.replace(R.id.nav_container, fragNDF, V2MainFragment.TAG);
                //transaction.addToBackStack(null);

                // Commit the transaction
                transactionNDF.commit();

                break;


            case Configuration.SCREENLAYOUT_SIZE_LARGE:


                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                NavigationDrawerFragment fragNDFL = new NavigationDrawerFragment();

                FragmentTransaction transactionNDFL = getSupportFragmentManager().beginTransaction();
                //transaction.setCustomAnimations(R.animator.fade_in_listoffres, R.animator.fade_out_listoffres);

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transactionNDFL.replace(R.id.nav_container, fragNDFL, V2MainFragment.TAG);
                //transaction.addToBackStack(null);

                // Commit the transaction
                transactionNDFL.commit();


                break;

            default:

                NavigationDrawerFragment NaDrawerFrag =
                        (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.frag_nav_drawer);

                NaDrawerFrag.setUp(
                        R.id.frag_nav_drawer,
                        findViewById(R.id.drawerLayout),
                        toolbar);
                break;
        }


        V2MainFragment frag = new V2MainFragment();
        frag.setListener(this);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.animator.fade_in_listoffres, R.animator.fade_out_listoffres);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.main_container, frag, V2MainFragment.TAG);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


        getCategories();


        //Show Interstitial Ads
        if (SHOW_ADS && SHOW_INTERSTITIAL_ADS_IN_STARTUP) {
            //show ad
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestNewInterstitial();
                }
            }, 5000);
        }
        UserController.checkUserConnection(this);

        /*(new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getUpcomingEvent();
            }
        }, 6000); */
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        mainMenu = menu;
        //updateBadge();

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.search_icon).setVisible(true);

        /*if (SessionsController.isLogged()) {
            menu.findItem(R.id.logout_icon).setVisible(true);
        } else {
            menu.findItem(R.id.logout_icon).setVisible(false);

        }*/
        menu.findItem(R.id.share_post).setVisible(false);

        return super.onPrepareOptionsMenu(menu);

    }

    /*private void updateBadge() {

        if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
            ActionItemBadge.update(this, mainMenu.findItem(R.id.item_samplebadge), CommunityMaterial.Icon.cmd_bell_ring_outline,
                    ActionItemBadge.BadgeStyles.RED,
                    MessengerHelper.NbrMessagesManager.getNbrTotalMessages());
        } else {
            ActionItemBadge.hide(mainMenu.findItem(R.id.item_samplebadge));
        }
    }*/

    //Manage menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.map_action) {

            startActivity(new Intent(this, MapStoresListActivity.class));
            overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);


        }/* else if (R.id.item_samplebadge == item.getItemId()) {

            // MainFragment.getPager().setCurrentItem(3);
        } */ else if (item.getItemId() == R.id.search_icon) {

            startActivity(new Intent(this, CustomSearchActivity.class));
            overridePendingTransition(R.anim.lefttoright_enter, R.anim.lefttoright_exit);

        } else if (item.getItemId() == R.id.logout_icon) {

            SessionsController.logOut();
            finish();
            startActivity(new Intent(this, SplashActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        opened = true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void onNewNotifs(BusMessage bus) {

        if (bus.getType() == BusMessage.GET_NBR_NEW_NOTIFS) {
            if (AppConfig.APP_DEBUG)
                if (MessengerHelper.NbrMessagesManager.getNbrTotalMessages() > 0) {
                    Toast.makeText(this, "New message " + MessengerHelper.NbrMessagesManager.getNbrTotalMessages()
                            , Toast.LENGTH_LONG).show();
                }
            // updateBadge();
        }

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));


        Tools.setSystemBarColor(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTracker.setScreenName("Image~" + MainActivity.class.getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        /*try {
            updateBadge();
        } catch (Exception e) {
        }*/
    }

    //Get all categories from server and save them in  the database
    private void getCategories() {


        SimpleRequest request = new SimpleRequest(Request.Method.GET,
                Constances.API.API_USER_GET_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (APP_DEBUG) {
                        Log.i("catsResponse", response);
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    // Log.e("response", jsonObject.toString());
                    final CategoryParser mCategoryParser = new CategoryParser(jsonObject);
                    int success = Integer.parseInt(mCategoryParser.getStringAttr(Tags.SUCCESS));
                    if (success == 1) {
                        //database.deleteCats();
                        //update list categories
                        CategoryController.insertCategories(
                                mCategoryParser.getCategories()
                        );

                    }

                } catch (JSONException e) {
                    //send a rapport to support
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (APP_DEBUG) {
                    Log.e("ERROR", error.toString());
                }

            }


        }) {


        };

        request.setRetryPolicy(new DefaultRetryPolicy(SimpleRequest.TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();
        String event;
        if (extras != null) {
            event = extras.getString("Notified");
            if (APP_DEBUG) {
                Log.i("Notified", "Event notified  " + event);
            }
        } else {
            if (APP_DEBUG) {
                Log.i("Notified", "Extras are NULL");
            }

        }
    }

    private void requestNewInterstitial() {

        if (AppConfig.APP_DEBUG)
            Toast.makeText(this, "requestNewInterstitial:" + getResources()
                    .getString(R.string.ad_interstitial_id), Toast.LENGTH_LONG).show();
        // Load Interstitial Ad
        // Initializing InterstitialAd - ADMob
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.ad_interstitial_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("4A55E4EA2535643C0D74A5A73C4F550A")
                .addTestDevice("3CB74DFA141BF4D0823B8EA7D94531B5")
                .build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // Full screen advertise will show only after showLoading complete
                mInterstitialAd.show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (NavigationDrawerFragment.getInstance() != null)
            NavigationDrawerFragment.getInstance().closeDrawers();

        V2MainFragment mf = (V2MainFragment) getSupportFragmentManager().findFragmentByTag(V2MainFragment.TAG);

        if (mf.ifFirstFragment()) {
            if (AppConfig.RATE_US_FORCE) {
                if (SettingsController.rateOnApp(this)) {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }

        } else {
            mf.setCurrentFragment(0);
        }

    }

    @Override
    public void onScrollHorizontal(int pos) {
        Log.e("onScrollHorizontal", " Pos- " + pos);

        if (pos == 0) {
            hide_default_toolbar();
        } else {
            show_default_toolbar();
        }
    }

    @Override
    public void onScrollVertical(int scrollXs, int scrollY) {


        Log.e("onScrollVertical", " scrollY- " + scrollY);

        if (scrollY > 600)
            show_default_toolbar();
        else
            hide_default_toolbar();

    }

    /*
     * Hide home header
     */


    private void show_default_toolbar() {

        TextView titleToolbar = toolbar.findViewById(R.id.textToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && titleToolbar != null) {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
            titleToolbar.setTextColor(getColor(R.color.white));
            titleToolbar.setVisibility(View.VISIBLE);
        }

        toolbar.setVisibility(View.VISIBLE);

    }

    /*
     * Show home header
     */


    private void hide_default_toolbar() {

        TextView titleToolbar = toolbar.findViewById(R.id.textToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && titleToolbar != null) {
            toolbar.setBackground(getDrawable(R.drawable.v2_toolbar_gradient));
            titleToolbar.setTextColor(getColor(R.color.white));
            titleToolbar.setVisibility(View.GONE);
        }

        toolbar.setVisibility(View.GONE);

    }


}
