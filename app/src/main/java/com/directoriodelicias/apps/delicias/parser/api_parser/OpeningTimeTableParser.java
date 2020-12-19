package com.directoriodelicias.apps.delicias.parser.api_parser;

import com.directoriodelicias.apps.delicias.classes.OpeningTime;
import com.directoriodelicias.apps.delicias.parser.Parser;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;


public class OpeningTimeTableParser extends Parser {
    public OpeningTimeTableParser(JSONObject json) {
        super(json);
    }


    public RealmList<OpeningTime> getList() {

        RealmList<OpeningTime> list = new RealmList<OpeningTime>();

        try {

            for (int i = 0; i < json.length(); i++) {


                JSONObject json_user = json.getJSONObject(i + "");
                OpeningTime mOpeningTime = new OpeningTime();

                mOpeningTime.setId(json_user.getInt("id"));
                mOpeningTime.setStore_id(json_user.getInt("store_id"));
                mOpeningTime.setOpening(json_user.getString("opening"));
                mOpeningTime.setDay(json_user.getString("day"));
                mOpeningTime.setClosing(json_user.getString("closing"));
                mOpeningTime.setEnabled(json_user.getInt("enabled"));
                mOpeningTime.setTimezone(json_user.getString("timezone"));

                list.add(mOpeningTime);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


}
