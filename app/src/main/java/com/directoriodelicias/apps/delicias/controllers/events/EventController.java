package com.directoriodelicias.apps.delicias.controllers.events;

import android.util.Log;

import com.directoriodelicias.apps.delicias.appconfig.AppConfig;
import com.directoriodelicias.apps.delicias.classes.Event;
import com.directoriodelicias.apps.delicias.classes.EventNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Directorio on 7/12/2017.
 */

public class EventController {


    public static void removeAll() {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Event> result = realm.where(Event.class).findAll();
                for (Event o : result) {
                    o.deleteFromRealm();
                }
            }
        });

    }

    private static boolean isTimeToNotify(String date) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        input.setTimeZone(TimeZone.getTimeZone("UTC"));


        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        output.setTimeZone(TimeZone.getDefault());

        Date eventDate = null;
        try {

            //event date
            eventDate = input.parse(date);
            String eventStrDate = output.format(eventDate);

            //current date

            SimpleDateFormat outputc = new SimpleDateFormat("yyyy-MM-dd");
            outputc.setTimeZone(TimeZone.getDefault());

            if (AppConfig.APP_DEBUG)
                Log.e("notifyIfExist", "eventDate: " + eventStrDate);


            Calendar timerc = Calendar.getInstance();
            String currentStrDate = outputc.format(timerc.getTime());

            if (AppConfig.APP_DEBUG)
                Log.e("notifyIfExist", "clientDate: " + currentStrDate);


            if (currentStrDate.equals(eventStrDate)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;

    }


    public static void addEventToNotify(final Event event) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventNotification mEventNotification = new EventNotification();
                if (event.isLiked()) {
                    mEventNotification.setId(event.getId());
                    mEventNotification.setEvent(event);
                    realm.copyToRealmOrUpdate(mEventNotification);
                }


            }
        });

    }

    public static boolean insertEvents(final RealmList<Event> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Event str : list) {
                    Event hasStore = realm.where(Event.class).equalTo("id", str.getId()).findFirst();
                    if (hasStore != null && hasStore.isLoaded()) {
                        str.setLiked(hasStore.isLiked());
                        realm.copyToRealmOrUpdate(str);
                    } else {
                        realm.copyToRealmOrUpdate(str);
                    }
                }

            }
        });
        return true;
    }

    public static Event doSave(final int id, final int status) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Event obj = realm.where(Event.class).equalTo("id", id).findFirst();
        obj.setSaved(status);
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();

        return obj;
    }


    public static boolean isEventLiked(int id) {

        Realm realm = Realm.getDefaultInstance();
        Event event = realm.where(Event.class).equalTo("id", id).equalTo("liked", true).findFirst();

        return event != null;
    }

    public static boolean doLike(final int id) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event event = realm.where(Event.class).equalTo("id", id).findFirst();
                if (event != null) {
                    event.setLiked(true);
                    realm.copyToRealm(event);
                }
            }
        });

        return false;
    }

    public static boolean doDisLike(final int id) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event event = realm.where(Event.class).equalTo("id", id).findFirst();
                event.setLiked(false);
                realm.copyToRealm(event);
            }
        });


        return false;
    }

    public static RealmList<Event> list() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Event.class).findAll();

        RealmList<Event> results = new RealmList<Event>();
        results.addAll(result.subList(0, result.size()));

        return results;
    }


    public static Event getEvent(int id) {

        Realm realm = Realm.getDefaultInstance();
        Event result = realm.where(Event.class).equalTo("id", id).findFirst();
        return result;
    }


    public static List<Event> getLikeEventsAsArrayList() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Event.class).equalTo("liked", true).findAll();

        List<Event> array = new ArrayList<>();

        array.addAll(result.subList(0, result.size()));
        return array;
    }


}
