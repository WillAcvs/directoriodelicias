package com.directoriodelicias.apps.delicias.controllers.sessions;

import com.directoriodelicias.apps.delicias.classes.Guest;

import io.realm.Realm;

/**
 * Created by Directorio on 11/20/2017.
 */

public class GuestController {

    public static void saveGuest(final Guest guest) {

        SessionsController.getLocalDatabase.setGuestId(guest.getId());

        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(guest);
            }
        });

    }

    public static Guest getGuest() {

        Realm mRealm = Realm.getDefaultInstance();
        return mRealm.where(Guest.class).findFirst();

    }

    public static void clear() {

        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Guest g = realm.where(Guest.class).findFirst();
                try {
                    g.deleteFromRealm();
                } catch (Exception e) {

                }
            }
        });

    }


    public static boolean isStored() {

        Realm mRealm = Realm.getDefaultInstance();
        Guest guest = mRealm.where(Guest.class).findFirst();

        return guest != null && guest.isValid() && guest.isManaged();
    }


}
