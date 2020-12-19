package com.directoriodelicias.apps.delicias.utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Directorio on 8/5/2016.
 */

public class Translator {


    public static String DefaultLang = Locale.getDefault().getLanguage().toLowerCase();


    public static String print(String str) {
        String value = print(str, null);

        //%s to str value
        return value;
    }

    public static String print(String str, String comment, Object... values) {

        //%s to str value
        return str;
    }

    public static String print(String str, String comment) {
        return str;
    }


    public static String getString(String str) {

        String value = str;

        try {

            String lang = DefaultLang.trim().toLowerCase();

            JSONObject js = new JSONObject(str);

            if (js.has(lang)) {
                value = js.getString(lang);
            } else if (js.has("en")) {
                value = js.getString("en");
            } else {
                value = str;
            }

        } catch (JSONException jex) {

            value = str;
        } catch (Exception e) {
            value = "";
        }


        return value;
    }


}
