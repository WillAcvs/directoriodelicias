package com.directoriodelicias.apps.delicias.animation;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class ImageLoaderAnimation {

    public static CircularProgressDrawable glideLoader(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }
}
