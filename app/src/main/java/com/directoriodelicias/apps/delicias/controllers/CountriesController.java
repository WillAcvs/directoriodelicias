package com.directoriodelicias.apps.delicias.controllers;

import com.directoriodelicias.apps.delicias.classes.CountriesModel;

import io.realm.Realm;

/**
 * Created by Directorio on 7/12/2017.
 */

public class CountriesController {

    public static CountriesModel findByDialCode(String code) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CountriesModel.class).equalTo("dial_code", code).findFirst();
    }

}
