package com.directoriodelicias.apps.delicias.parser.api_parser;


import com.directoriodelicias.apps.delicias.appconfig.AppContext;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;

/**
 * Created by Directorio on 2/4/2016.
 */
public class ImagesParser extends Parser {


    public ImagesParser(JSONObject json) {
        super(json);
    }

    public RealmList<Images> getGallery() {
        RealmList<Images> list = new RealmList<Images>();


        try {

            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {


                JSONObject js = json_array.getJSONObject(i + "");
                Images images = new Images();

                JSONObject json_images_200_200 = js.getJSONObject("200_200");
                images.setUrl200_200(json_images_200_200.getString("url"));

                JSONObject json_images_500_500 = js.getJSONObject("560_560");
                images.setUrl500_500(json_images_500_500.getString("url"));

                JSONObject json_images_100_100 = js.getJSONObject("100_100");
                images.setUrl100_100(json_images_100_100.getString("url"));

                JSONObject json_images_full = js.getJSONObject("full");
                images.setUrlFull(json_images_full.getString("url"));
                images.setId(js.getString("name"));
                images.setJson(js);

                try {

                    images.setHeight(json_images_full.getInt("height"));
                    images.setWidth(json_images_full.getInt("width"));

                } catch (JSONException e) {
                    if (AppContext.DEBUG) {
                        e.printStackTrace();
                    }
                }

                list.add(images);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;

    }


    public RealmList<Images> getImagesList() {
        RealmList<Images> list = new RealmList<Images>();

        try {


            for (int i = 0; i < json.length(); i++) {


                JSONObject js = json.getJSONObject(i + "");
                Images images = new Images();

                if (js.has("200_200")) {
                    JSONObject json_images_200_200 = js.getJSONObject("200_200");
                    images.setUrl200_200(json_images_200_200.getString("url"));
                }

                if (js.has("560_560")) {
                    JSONObject json_images_500_500 = js.getJSONObject("560_560");
                    images.setUrl500_500(json_images_500_500.getString("url"));
                }

                if (js.has("100_100")) {
                    JSONObject json_images_100_100 = js.getJSONObject("100_100");
                    images.setUrl100_100(json_images_100_100.getString("url"));
                }

                if (js.has("full")) {
                    JSONObject json_images_full = js.getJSONObject("full");
                    images.setUrlFull(json_images_full.getString("url"));
                }

                images.setId(js.getString("name"));
                images.setJson(js);

               /* try {

                    images.setHeight(json_images_full.getInt("height"));
                    images.setWidth(json_images_full.getInt("width"));

                }catch (JSONException e){
                    if(AppContext.DEBUG){
                        e.printStackTrace();
                    }
                }*/

                list.add(images);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;

    }


    public Images getImage() {

        Images images = null;


        try {

            if (json.length() > 0) {

                images = new Images();

                images.setId(json.getString("name"));

                JSONObject json_images_200_200 = json.getJSONObject("200_200");
                images.setUrl200_200(json_images_200_200.getString("url"));

                JSONObject json_images_500_500 = json.getJSONObject("560_560");
                images.setUrl500_500(json_images_500_500.getString("url"));


                JSONObject json_images_100_100 = json.getJSONObject("100_100");
                images.setUrl100_100(json_images_100_100.getString("url"));

                JSONObject json_images_full = json.getJSONObject("full");
                images.setUrlFull(json_images_full.getString("url"));

                images.setJson(json);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return images;
    }

}
