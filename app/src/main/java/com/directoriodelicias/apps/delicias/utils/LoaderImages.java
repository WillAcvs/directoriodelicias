package com.directoriodelicias.apps.delicias.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;

import java.io.InputStream;

/**
 * Created by Directorio on 30/10/2015.
 */
public class LoaderImages extends AsyncTask<String, Void, Bitmap> {

    public ImageView bmImage;

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            if (AppConfig.APP_DEBUG) {
                Log.e("Error", e.getMessage());
            }

            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) bmImage.setImageBitmap(result);
    }
}
