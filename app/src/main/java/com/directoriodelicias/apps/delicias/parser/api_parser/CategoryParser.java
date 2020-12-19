package com.directoriodelicias.apps.delicias.parser.api_parser;

import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Category;
import com.directoriodelicias.apps.delicias.parser.Parser;
import com.directoriodelicias.apps.delicias.parser.tags.Tags;
import com.directoriodelicias.apps.delicias.v2.model.CategoryM;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;


public class CategoryParser extends Parser {
    public CategoryParser(JSONObject json) {
        super(json);
    }


    public RealmList<Category> getCategories() {

        RealmList<Category> list = new RealmList<Category>();

        try {

            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {

                JSONObject json_user = json_array.getJSONObject(i + "");
                Category cat = new Category();
                cat.setNumCat(json_user.getInt("id_category"));
                cat.setNameCat(json_user.getString("name"));
                cat.setParentCategory(json_user.getInt("parent_id"));
                if (!json_user.isNull("color")) cat.setColor(json_user.getString("color"));
                if (!json_user.isNull("_order")) cat.set_order(json_user.getInt("_order"));
                cat.setMenu(false);


                if (!json_user.isNull("image") && !json_user.getString("image").equals("")) {
                    ImagesParser mImagesParser = new ImagesParser(
                            new JSONObject(json_user.getString("image"))
                    );
                    cat.setImages(mImagesParser.getImage());
                }

                if (!json_user.isNull("icon")) {
                    ImagesParser mIconParser = new ImagesParser(
                            new JSONObject(json_user.getString("icon"))
                    );
                    cat.setLogo(mIconParser.getImage());
                }


                try {
                    cat.setNbr_stores(json_user.getInt("nbr_stores"));
                } catch (Exception e) {
                    cat.setNbr_stores(0);
                }


                if (AppConfig.APP_DEBUG)
                    Log.i("categoryImages", json_user.getString("image"));


                list.add(cat);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }


        return list;
    }


    public RealmList<CategoryM> getCategoriesM() {

        RealmList<CategoryM> list = new RealmList<CategoryM>();

        try {

            JSONObject json_array = json.getJSONObject(Tags.RESULT);

            for (int i = 0; i < json_array.length(); i++) {


                try {
                    JSONObject json_user = json_array.getJSONObject(i + "");
                    CategoryM cat = new CategoryM();
                    cat.setNum(json_user.getInt("id_category"));
                    cat.setName(json_user.getString("name"));

                    try {
                        ImagesParser mImagesParser = new ImagesParser(
                                new JSONObject(json_user.getString("image"))
                        );
                        cat.setImages(mImagesParser.getImage());
                    } catch (Exception e) {
                        if (AppConfig.APP_DEBUG)
                            e.printStackTrace();
                    }

                    try {
                        cat.setNbr_stores(json_user.getInt("nbr_stores"));
                    } catch (Exception e) {
                        cat.setNbr_stores(0);
                    }

                    if (AppConfig.APP_DEBUG)
                        Log.i("categoryMImages", json_user.getString("image"));


                    list.add(cat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }


}
