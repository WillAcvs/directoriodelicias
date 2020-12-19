package com.directoriodelicias.apps.delicias.utils;


import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.directoriodelicias.apps.delicias.unbescape.html.HtmlEscape;

/**
 * Created by Droideve on 1/3/2017.
 */

public class TextUtils {

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static class decodeHtml extends AsyncTask<String, String, String> {

        private TextView view;

        public decodeHtml(View v) {
            this.view = (TextView) v;
        }

        @Override
        protected void onPostExecute(final String text) {
            super.onPostExecute(text);
            view.setText(Html.fromHtml(text));
            //eventData.setDescription(text);
        }

        @Override
        protected String doInBackground(String... params) {

            return HtmlEscape.unescapeHtml(params[0]);
        }
    }


}
