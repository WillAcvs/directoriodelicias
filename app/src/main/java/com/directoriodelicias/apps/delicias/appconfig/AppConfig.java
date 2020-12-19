package com.directoriodelicias.apps.delicias.appconfig;

import com.directoriodelicias.apps.delicias.classes.Category;

import java.util.List;

public class AppConfig {

    //Set the link to the app store account
    public static final String PLAY_STORE_URL = "";
    public static String BASE_URL = "";
    public static List<Category> TabsConfig = null;

    // To verify if the app is build on Debug r Release Mode
    public static boolean APP_DEBUG = true;


    public static boolean NOTIFICATION_SOUND = true;


    public static boolean SAFE_MODE = false;

    // Set to true if you want to display ads in all views.
    public static boolean SHOW_ADS = false;
    public static boolean SHOW_ADS_IN_STORE = false;
    public static boolean SHOW_ADS_IN_EVENT = false;
    public static boolean SHOW_ADS_IN_OFFER = false;
    public static boolean SHOW_ADS_IN_HOME = false;
    public static boolean SHOW_INTERSTITIAL_ADS_IN_STARTUP = false;
    public static boolean ENABLE_LOCAL_MAPS_DIRECTION = false;

    public static boolean ENABLE_CHAT = false;
    public static boolean CHAT_WITH_FIREBASE = true;
    public static String ANDROID_API_KEY = "";


    public static boolean ENABLE_WEB_DASHBOARD = true;
    public static boolean RATE_US_FORCE = true;

    // ENABLE INTRO SLIDER WHEN THE APP START FOR THE FIRST TIME
    public static boolean ENABLE_INTRO_SLIDER = true;
    public static boolean ENABLE_OFFERS_FILTRE = true;

    public static int OFFERS_NUMBER_PER_ROW;

    public static boolean ENABLE_PEOPLE_AROUND_ME = false;

    public static String ADDRESS_CONTACT = "";
    public static String PHONE = "";


    public static String ABOUT_CONTENT = "";


    public static class GCMConfig {
        // flag to identify whether to show single line
        // or multi line text in push notification tray
        public static boolean appendNotificationMessages = false;

    }
}
