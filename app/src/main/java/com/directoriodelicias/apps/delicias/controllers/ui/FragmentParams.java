package com.directoriodelicias.apps.delicias.controllers.ui;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Directorio on 11/5/2016.
 */

public class FragmentParams implements Serializable {

    protected int fragment_type;
    protected boolean list_refresh;
    protected String list_type;
    protected String content_type;
    protected boolean appUser = false;
    protected transient JSONObject customeData;
    protected String json;


    private void stringToJson() {
        try {

            if (customeData == null)
                customeData = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonToString() {
        json = customeData.toString();
    }


}
