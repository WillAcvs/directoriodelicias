package com.directoriodelicias.apps.delicias.parser.api_parser;


import android.content.Context;

import com.directoriodelicias.apps.delicias.classes.Notification;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;


public class NotificationParser extends Parser {

    public NotificationParser(JSONObject json) {
        super(json);
    }

    public RealmList<Notification> getNotifications(Context context) {

        RealmList<Notification> list = new RealmList<Notification>();

        try {


            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {

                try {
                    JSONObject json_user = json_array.getJSONObject(i + "");
                    Notification notif = new Notification();
                    notif.setId(json_user.getInt("id"));
                    notif.setLabel(json_user.getString("label"));
                    notif.setLabel_description(json_user.getString("label_description"));
                    notif.setDetail(json_user.getString("detail"));
                    notif.setImage(json_user.getString("image"));
                    notif.setModule_id(json_user.getInt("module_id"));
                    notif.setModule(json_user.getString("module"));
                    notif.setStatus(json_user.getInt("status"));
                    notif.setCreated_at(json_user.getString("created_at"));
                    notif.setUpdated_at(json_user.getString("updated_at"));
                    notif.setCompaigns_id(json_user.getInt("campaign_id"));

                    try {
                        String jsonValues = "";
                        if (!json_user.isNull("image")) {
                            jsonValues = json_user.getJSONObject("image").toString();
                            JSONObject jsonObject = new JSONObject(jsonValues);
                            ImagesParser imgp = new ImagesParser(jsonObject);

                            if (imgp.getImagesList().size() > 0) {
                                notif.setImages(imgp.getImagesList().get(0));

                            }

                        }

                    } catch (JSONException jex) {
                        notif.setImages(null);
                    }
                    list.add(notif);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


    public Notification getNotification(Context context) {

        try {


            JSONArray json_array = json.getJSONArray(Tags.RESULT);


            try {
                JSONObject json_user = json_array.getJSONObject(0);
                Notification notif = new Notification();
                notif.setId(json_user.getInt("id"));
                notif.setLabel(json_user.getString("label"));
                notif.setLabel_description(json_user.getString("label_description"));
                notif.setDetail(json_user.getString("detail"));
                notif.setImage(json_user.getString("image"));
                notif.setModule_id(json_user.getInt("module_id"));
                notif.setModule(json_user.getString("module"));
                notif.setStatus(json_user.getInt("status"));
                notif.setCreated_at(json_user.getString("created_at"));
                notif.setUpdated_at(json_user.getString("updated_at"));
                notif.setCompaigns_id(json_user.getInt("campaign_id"));

                return notif;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Notification getPushNotification(Context context) {


        try {
            Notification notif = new Notification();
            notif.setId(json.getInt("id"));
            notif.setLabel(json.getString("label"));
            notif.setLabel_description(json.getString("label_description"));
            notif.setDetail(json.getString("detail"));
            notif.setImage(json.getString("image"));
            notif.setModule_id(json.getInt("module_id"));
            notif.setModule(json.getString("module"));
            notif.setStatus(json.getInt("status"));
            notif.setCreated_at(json.getString("created_at"));
            notif.setUpdated_at(json.getString("updated_at"));
            notif.setCompaigns_id(json.getInt("campaign_id"));

            return notif;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


}
