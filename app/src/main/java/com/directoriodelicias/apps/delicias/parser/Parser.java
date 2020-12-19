package com.directoriodelicias.apps.delicias.parser;


import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Droideve on 1/10/2016.
 */
public class Parser {

    protected JSONObject json;

    public Parser(JSONObject json) {

        this.json = json;

    }


    public int getSuccess() {
        return Integer.parseInt(getStringAttr(Tags.SUCCESS));
    }


    /*
     * Here we can create all method to get list of product or an other object
     */

    public int getIntArg(String tag) {

        int i = 0;
        try {

            if (json.has(Tags.ARGS))
                if (json.getJSONObject(Tags.ARGS).has(tag))
                    i = json.getJSONObject(Tags.ARGS).getInt(tag);


        } catch (JSONException e) {
        }

        return i;
    }


    public String getStringAttr(String tag) {

        String i = "";
        try {
            if (json.has(tag))
                i = json.getString(tag);
        } catch (JSONException e) {
        }
        return i;
    }


    public Map<String, String> getErrors() {

        Map<String, String> list = new HashMap<String, String>();

        try {

            JSONObject json_array = json.getJSONObject(Tags.ERRORS);


            Iterator<String> keysItr = json_array.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = json_array.getString(key);
                list.put(key, value);
            }

        } catch (JSONException e) {

        }

        return list;
    }


}
