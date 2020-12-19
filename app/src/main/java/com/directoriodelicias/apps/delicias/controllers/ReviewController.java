package com.directoriodelicias.apps.delicias.controllers;

import com.directoriodelicias.apps.delicias.classes.Guest;
import com.directoriodelicias.apps.delicias.classes.Review;
import com.directoriodelicias.apps.delicias.controllers.sessions.GuestController;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Directorio on 12/25/2017.
 */

public class ReviewController {


    public static boolean isRated(int store_id) {

        Guest guest = GuestController.getGuest();
        Realm mRealm = Realm.getDefaultInstance();

        try {

            RealmResults<Review> result = mRealm.where(Review.class)
                    .equalTo("store_id", store_id)
                    .equalTo("guest_id", guest.getId()).findAll();


            if (result.isLoaded() && result.isValid() && result.size() > 0)
                return true;

        } catch (Exception e) {

        }

        return false;
    }

}
