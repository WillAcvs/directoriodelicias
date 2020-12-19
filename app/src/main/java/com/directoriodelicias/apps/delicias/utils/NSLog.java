package com.directoriodelicias.apps.delicias.utils;

import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;

public class NSLog {
    public static void e(String tag, String msg) {
        if (AppConfig.APP_DEBUG)
            Log.e(tag, msg);
    }

    public static void e(String tag, int msg) {
        if (AppConfig.APP_DEBUG)
            Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, double msg) {
        if (AppConfig.APP_DEBUG)
            Log.e(tag, String.valueOf(msg));
    }
}