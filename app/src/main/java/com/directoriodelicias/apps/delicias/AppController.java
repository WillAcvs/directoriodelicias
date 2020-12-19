package com.directoriodelicias.apps.delicias;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.appconfig.Constances;
import com.directoriodelicias.apps.delicias.helper.MyPreferenceManager;
import com.directoriodelicias.apps.delicias.network.ServiceHandler;
import com.directoriodelicias.apps.delicias.security.Security;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Droideve on 6/2/2016.
 */
public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class
            .getSimpleName();
    private static String fcmToken = "";
    private static HashMap<String, String> tokens = null;
    private static ArrayList<String> listLangsIndex = null;
    private static AppController mInstance;
    private MyPreferenceManager pref;
    private Tracker mTracker;
    private RequestQueue mRequestQueue;

    public synchronized static boolean isTokenFound() {
        SharedPreferences sharedPref = getInstance().getSharedPreferences("tokens", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token-0", "");

        return !token.equals("");
    }

    public synchronized static HashMap<String, String> getTokens() {

        tokens = new HashMap<>();
        SharedPreferences sharedPref = getInstance().getSharedPreferences("tokens", Context.MODE_PRIVATE);

        tokens.put("apiKey", "00-1");
        tokens.put("macadr", sharedPref.getString("macadr", ServiceHandler.getMacAddr()));
        tokens.put("token-0", sharedPref.getString("token-0", ""));
        tokens.put("token-1", sharedPref.getString("token-1", ""));
        tokens.put("ipAddress", "value");

        if (AppContext.DEBUG)
            Log.e(TAG, "getTokens");

        return tokens;
    }

    public synchronized static HashMap<String, String> setTokens(String macadr, String token0, String token1) {
        SharedPreferences sharedPref = getInstance().getSharedPreferences("tokens", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("apiKey", "00-1");
        editor.putString("macadr", macadr);
        editor.putString("token-0", token0);
        editor.putString("token-1", token1);
        editor.putString("uid", token1);
        editor.putString("ipAddress", "value");
        editor.commit();

        tokens = new HashMap<>();
        tokens.put("apiKey", "00-1");
        tokens.put("macadr", macadr);
        tokens.put("token-0", token0);
        tokens.put("token-1", token1);
        tokens.put("ipAddress", "value");

        if (AppContext.DEBUG)
            Log.e(TAG, "setTokens");

        return tokens;
    }


    /*
        DCMESSENGER Init
     */

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }
        return pref;
    }

    private void appInit() {
        mInstance = this;
        parseAppConfig();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(getString(R.string.analytics));
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInit();

        Security.init(this);


        MobileAds.initialize(this, getString(R.string.ad_app_id));
        //initialization typeface (fonts)

        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(BuildConfig.APPLICATION_ID + ".realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        //get langs

        FirebaseApp.initializeApp(this);

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
        if (AppConfig.APP_DEBUG) {
            Log.e("Application", "Memory  cleaned !!");
        }
    }

    private void parseAppConfig() {

        //others
        AppConfig.ABOUT_CONTENT = getResources().getString(R.string.ABOUT_CONTENT);
        AppConfig.ADDRESS_CONTACT = getResources().getString(R.string.ADDRESS_CONTACT);
        AppConfig.PHONE = getResources().getString(R.string.PHONE);

        AppConfig.SHOW_ADS = Boolean.parseBoolean(getResources().getString(R.string.SHOW_ADS));
        AppConfig.SHOW_ADS_IN_EVENT = Boolean.parseBoolean(getResources().getString(R.string.SHOW_ADS_IN_EVENT));
        AppConfig.SHOW_ADS_IN_HOME = Boolean.parseBoolean(getResources().getString(R.string.SHOW_ADS_IN_HOME));
        AppConfig.SHOW_INTERSTITIAL_ADS_IN_STARTUP = Boolean.parseBoolean(getResources().getString(R.string.SHOW_INTERSTITIAL_ADS_IN_STARTUP));
        AppConfig.SHOW_ADS_IN_STORE = Boolean.parseBoolean(getResources().getString(R.string.SHOW_ADS_IN_STORE));
        AppConfig.SHOW_ADS_IN_OFFER = Boolean.parseBoolean(getResources().getString(R.string.SHOW_ADS_IN_OFFER));

        AppConfig.BASE_URL = getResources().getString(R.string.BASE_URL);

        AppConfig.ENABLE_CHAT = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_CHAT));

        AppConfig.ENABLE_WEB_DASHBOARD = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_WEB_DASHBOARD));
        AppConfig.RATE_US_FORCE = Boolean.parseBoolean(getResources().getString(R.string.RATE_US_ON_PLAY_STORE_FORCE));
        AppConfig.ANDROID_API_KEY = getResources().getString(R.string.ANDROID_API_KEY);
        AppConfig.ENABLE_INTRO_SLIDER = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_INTRO_SLIDER));
        AppConfig.ENABLE_OFFERS_FILTRE = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_FILTRE_BAR));
        AppConfig.OFFERS_NUMBER_PER_ROW = Integer.parseInt(getResources().getString(R.string.OFFERS_NUMBER_PER_ROW));
        AppConfig.ENABLE_LOCAL_MAPS_DIRECTION = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_LOCAL_MAPS_DIRECTION));


        //chat config
        Constances.BASE_URL = getResources().getString(R.string.BASE_URL);
        Constances.BASE_URL_API = getResources().getString(R.string.BASE_URL_API);
        Constances.PRIVACY_POLICY_URL = getResources().getString(R.string.PRIVACY_POLICY_URL);
        Constances.TERMS_OF_USE_URL = getResources().getString(R.string.TERMS_OF_USE_URL);


        AppConfig.ENABLE_PEOPLE_AROUND_ME = Boolean.parseBoolean(getResources().getString(R.string.ENABLE_PEOPLE_AROUND_ME));


    }

    public void updateAndroidSecurityProvider(Activity callingActivity) {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), callingActivity, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("SecurityException", "Google Play Services not available.");
        }
    }


}
