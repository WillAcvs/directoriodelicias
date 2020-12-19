package com.directoriodelicias.apps.delicias.controllers.stores;

import com.directoriodelicias.apps.delicias.classes.SavedStores;
import com.directoriodelicias.apps.delicias.classes.Store;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Droideve on 7/12/2017.
 */

public class StoreController {


    public static boolean insertStores(final RealmList<Store> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Store str : list) {
                    realm.copyToRealmOrUpdate(str);
                }

            }
        });
        return true;
    }

    public static Store findStoreById(int id) {

        Realm realm = Realm.getDefaultInstance();
        Store obj = realm.where(Store.class).equalTo("id", id).findFirst();

        return obj;
    }

    public static Store getStore(int id) {

        Realm realm = Realm.getDefaultInstance();
        Store obj = realm.where(Store.class).equalTo("id", id).findFirst();

        return obj;
    }


    public static Store doSave(final int id, final int status) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Store obj = realm.where(Store.class).equalTo("id", id).findFirst();
        obj.setSaved(status);
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();

        return obj;
    }


    public static boolean isSaved(int id) {

        Realm realm = Realm.getDefaultInstance();

        SavedStores saved_stores = realm.where(SavedStores.class).findFirst();

        if (saved_stores == null) {
            saved_stores = new SavedStores();
            saved_stores.setId(1);
        }

        if (saved_stores != null) {
            return saved_stores.isExist(id);
        }

        return false;
    }


    public static String getSavedStoresAsString() {
        Realm realm = Realm.getDefaultInstance();

        SavedStores saved_stores = realm.where(SavedStores.class).findFirst();

        if (saved_stores == null) {
            saved_stores = new SavedStores();
            saved_stores.setId(1);
        }

        String ids = "";

        if (saved_stores != null && saved_stores.getListID() != null)
            for (int i = 0; i < saved_stores.getListID().size(); i++) {
                ids = ids + "," + saved_stores.getListID().get(i);
            }


        return ids;
    }

    public static boolean doDelete(int id) {

        Realm realm = Realm.getDefaultInstance();
        final Store obj = realm.where(Store.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //obj.setSaved(false);
                realm.copyToRealm(obj);
            }
        });


        SavedStores saved_stores = realm.where(SavedStores.class).findFirst();

        if (saved_stores == null) {
            saved_stores = new SavedStores();
            saved_stores.setId(1);
        }

        if (saved_stores != null) {
            if (saved_stores.isExist(id)) {
                realm.beginTransaction();
                saved_stores.delete(id);
                realm.copyToRealmOrUpdate(saved_stores);
                realm.commitTransaction();
            }
        }


        return false;
    }

    public static RealmList<Store> list() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Store.class).findAll();

        RealmList<Store> results = new RealmList<Store>();
        results.addAll(result.subList(0, result.size()));

        return results;
    }

    public static void removeAll() {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Store> result = realm.where(Store.class).findAll();
                for (Store o : result) {
                    o.deleteFromRealm();
                }
            }
        });

    }

}
