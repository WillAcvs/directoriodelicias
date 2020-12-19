package com.directoriodelicias.apps.delicias.parser.api_parser;


import android.content.Context;

import com.directoriodelicias.apps.delicias.classes.Bookmark;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;


public class BookmarkParser extends Parser {

    public BookmarkParser(JSONObject json) {
        super(json);
    }

    public RealmList<Bookmark> getBookmarks(Context context) {

        RealmList<Bookmark> list = new RealmList<Bookmark>();

        try {


            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {

                try {
                    JSONObject json_bookmark = json_array.getJSONObject(i + "");
                    Bookmark bookmark = new Bookmark();
                    bookmark.setId(json_bookmark.getInt("id"));
                    bookmark.setLabel(json_bookmark.getString("label"));
                    bookmark.setLabel_description(json_bookmark.getString("label_description"));
                    bookmark.setModule_id(json_bookmark.getInt("module_id"));
                    bookmark.setModule(json_bookmark.getString("module"));
                    //bookmark.setStatus(json_bookmark.getInt("status"));
                    if (!json_bookmark.isNull("guest_id"))
                        bookmark.setGuest_id(json_bookmark.getInt("guest_id"));
                    bookmark.setUser_id(json_bookmark.getInt("user_id"));
                    bookmark.setNotification_agreement(json_bookmark.getInt("notification_agreement"));


                    try {
                        String jsonValues = "";
                        if (!json_bookmark.isNull("image")) {
                            jsonValues = json_bookmark.getJSONObject("image").toString();
                            JSONObject jsonObject = new JSONObject(jsonValues);
                            ImagesParser imgp = new ImagesParser(jsonObject);

                            if (imgp.getImagesList().size() > 0) {
                                bookmark.setImages(imgp.getImagesList().get(0));

                            }

                        }

                    } catch (JSONException jex) {
                        bookmark.setImages(null);
                    }
                    list.add(bookmark);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


    public Bookmark getBookmark(Context context) {

        try {


            JSONArray json_array = json.getJSONArray(Tags.RESULT);


            try {
                JSONObject json_bookmark = json_array.getJSONObject(0);
                Bookmark bookmark = new Bookmark();
                bookmark.setId(json_bookmark.getInt("id"));
                bookmark.setLabel(json_bookmark.getString("label"));
                bookmark.setLabel_description(json_bookmark.getString("label_description"));
                bookmark.setModule_id(json_bookmark.getInt("module_id"));
                bookmark.setModule(json_bookmark.getString("module"));
                bookmark.setStatus(json_bookmark.getInt("status"));
                bookmark.setGuest_id(json_bookmark.getInt("guest_id"));
                bookmark.setUser_id(json_bookmark.getInt("user_id"));
                bookmark.setNotification_agreement(json_bookmark.getInt("notification_agreement"));

                try {
                    String jsonValues = "";
                    if (!json_bookmark.isNull("image")) {
                        jsonValues = json_bookmark.getJSONObject("image").toString();
                        JSONObject jsonObject = new JSONObject(jsonValues);
                        ImagesParser imgp = new ImagesParser(jsonObject);

                        if (imgp.getImagesList().size() > 0) {
                            bookmark.setImages(imgp.getImagesList().get(0));

                        }

                    }

                } catch (JSONException jex) {
                    bookmark.setImages(null);
                }


                return bookmark;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


}
