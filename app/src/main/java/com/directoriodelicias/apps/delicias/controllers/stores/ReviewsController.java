package com.directoriodelicias.apps.delicias.controllers.stores;

import com.directoriodelicias.apps.delicias.classes.Review;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Directorio on 11/12/2017.
 */

public class ReviewsController {

    public static List<Review> findReviewyStoreId(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Review.class).equalTo("store_id", id).findAllSorted("rate", Sort.DESCENDING);
        List<Review> array = new ArrayList<>();
        array.addAll(result.subList(0, result.size()));
        return array;
    }

    public static boolean insertReviews(final RealmList<Review> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Review review : list) {
                    realm.copyToRealmOrUpdate(review);
                }
            }
        });
        return true;
    }


    public static void deleteAllReviews(int id) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults result = realm.where(Review.class).equalTo("store_id", id).findAllSorted("rate", Sort.DESCENDING);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });

    }


}
