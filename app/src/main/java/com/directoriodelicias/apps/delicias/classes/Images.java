package com.directoriodelicias.apps.delicias.classes;

import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Directorio on 2/4/2016.
 */
public class Images extends RealmObject implements Serializable {

    @Ignore
    public static int USER = 2;
    private String url200_200;
    private String url500_500;
    private String url100_100;
    private int height;
    private int width;
    @Ignore
    private JSONObject json;
    private String mJson;
    @PrimaryKey
    private String id;
    private String hashtags = "";
    private int type = USER;
    private String urlFull;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlFull() {
        return urlFull;
    }

    public void setUrlFull(String urlFull) {
        this.urlFull = urlFull;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public String getUrl200_200() {
        return url200_200;
    }

    public void setUrl200_200(String url200_200) {
        this.url200_200 = url200_200;
    }

    public String getUrl500_500() {
        return url500_500;
    }

    public void setUrl500_500(String url500_500) {
        this.url500_500 = url500_500;
    }

    public String getUrl100_100() {
        return url100_100;
    }

    public void setUrl100_100(String url100_100) {
        this.url100_100 = url100_100;
    }
}
