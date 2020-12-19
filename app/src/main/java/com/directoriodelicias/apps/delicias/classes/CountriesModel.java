package com.directoriodelicias.apps.delicias.classes;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Abderrahim El imame on 8/13/16.
 *
 * @Email : abderrahim.elimame@gmail.com
 * @Author : https://twitter.com/bencherif_el
 */

public class CountriesModel extends RealmObject {

    @PrimaryKey
    @Expose
    private String name;
    @Expose
    private String dial_code;
    @Expose
    private String code;

    public String getDial_code() {
        return dial_code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
