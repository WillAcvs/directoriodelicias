package com.directoriodelicias.apps.delicias.controllers.sessions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Session;
import com.directoriodelicias.apps.delicias.classes.User;

import io.realm.Realm;

/**
 * Created by Droideve on 8/1/2016.
 */

public class SessionsController {

    private static final int aisession = 1;
    private static Realm mRealm = Realm.getDefaultInstance();
    private static Session session;

    public static boolean isLogged() {

        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }

        mRealm = Realm.getInstance(mRealm.getConfiguration());


        Session session = getSession();

        if (session != null && session.isValid()) {
            User user = session.getUser();
            return user != null && user.isValid();
        }

        return false;
    }

    public static Session getSession() {

        try {

            if (mRealm == null) {
                mRealm = Realm.getDefaultInstance();
            }
            mRealm = Realm.getInstance(mRealm.getConfiguration());
            session = mRealm.where(Session.class).equalTo("sessionId", aisession).findFirst();
            if (session == null) {
                session = new Session();
                session.setSessionId(aisession);
            }


        } catch (Exception e) {

            if (AppConfig.APP_DEBUG)
                e.printStackTrace();
        }


        return session;
    }


    public static void updateSession(final User user) {
        if (SessionsController.isLogged()) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(user);
                }
            });
        }
    }


    public static Session createSession(final User user) {

        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }

        mRealm = Realm.getInstance(mRealm.getConfiguration());
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(Session.class).findAll().deleteAllFromRealm();
                mRealm.where(User.class).findAll().deleteAllFromRealm();
            }
        });


        //Guest guest = SessionsController.getSession().getGuest();
        Session session = getSession();

        if (AppConfig.APP_DEBUG)
            Log.i("loggedUser", "_wait__");

        if (session != null) {

            session.setUser(user);
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(session);
            mRealm.commitTransaction();


            getLocalDatabase.setUserId(user.getId());

            if (AppConfig.APP_DEBUG)
                Log.i("loggedUser", "_ok__");
        }


        //aisession++;
        return session;

    }

    public static void logOut() {

        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }

        mRealm = Realm.getInstance(mRealm.getConfiguration());
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(Session.class).findAll().deleteAllFromRealm();
                mRealm.where(User.class).findAll().deleteAllFromRealm();
            }
        });

        getLocalDatabase.setUserId(0);

        GuestController.clear();
    }


    public static class getLocalDatabase {


        public static boolean isLogged() {

            return getUserId() > 0;
        }


        public static int getUserId() {

            SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("usession", Context.MODE_PRIVATE);
            return sharedPref.getInt("user_id", 0);
        }


        public static void setUserId(int id) {

            SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("usession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("user_id", id);
            editor.commit();

        }

        public static int getGuestId() {

            SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("usession", Context.MODE_PRIVATE);
            return sharedPref.getInt("guest_id", 0);
        }

        public static void setGuestId(int id) {

            SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("usession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("guest_id", id);
            editor.commit();

        }

    }


}
