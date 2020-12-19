package com.directoriodelicias.apps.delicias.controllers.banners;

import com.directoriodelicias.apps.delicias.classes.Banner;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class BannersController {

    public static List<Banner> getArrayList() {

        List<Banner> results = new ArrayList<>();
        RealmList<Banner> listCats = list();

        results.addAll(listCats.subList(0, listCats.size()));
        return results;
    }

    public static RealmList<Banner> list() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Banner.class).equalTo("status", 1).findAll();

        RealmList<Banner> results = new RealmList<Banner>();
        results.addAll(result.subList(0, result.size()));

        return results;
    }

    public static boolean insertBanner(final Banner cat) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

//               RealmResults<Banner> r = realm.where(Banner.class).findAll();
//                r.deleteAllFromRealm();

                realm.copyToRealmOrUpdate(cat);
            }
        });

        return true;
    }

    public static boolean insertBanners(final RealmList<Banner> list) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(list);
            }
        });

        return true;
    }


    public static Banner findId(int id) {

        Realm realm = Realm.getDefaultInstance();
        Banner obj = realm.where(Banner.class).equalTo("id", id).findFirst();

        return obj;
    }

    public static void removeAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Banner> result = realm.where(Banner.class).findAll();
                for (Banner cat : result) {
                    cat.deleteFromRealm();
                }
            }
        });

    }
}
