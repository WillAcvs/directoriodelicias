package com.directoriodelicias.apps.delicias.controllers.events;


import com.directoriodelicias.apps.delicias.classes.UpComingEvent;
import com.directoriodelicias.apps.delicias.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class UpComingEventsController {


    public static String getListAsString() {

        String value = "0,";

        List<Integer> list = UpComingEventsController.getUpComingEventID();

        for (int id : list) {
            value = value + id + ",";
        }

        return value;
    }

    public static List<UpComingEvent> getList() {

        List<UpComingEvent> list = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();

        RealmResults<UpComingEvent> result = realm.where(UpComingEvent.class).findAll();

        if (result.isLoaded() && result.isValid())
            for (UpComingEvent uce : result) {
                list.add(uce);
            }

        return list;

    }

    public static List<Integer> getUpComingEventID() {

        List<Integer> list = new ArrayList<>();

        List<UpComingEvent> result = UpComingEventsController.getList();

        if (result.size() > 0)
            for (UpComingEvent object : result) {
                if (DateUtils.isLessThan24(object.getBegin_at(), null))
                    list.add(object.getEvent_id());
            }


        return list;

    }

    public static List<UpComingEvent> getUpComingEventsNotNotified() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<UpComingEvent> result = realm.where(UpComingEvent.class).equalTo("notified", false).findAll();

        List<UpComingEvent> list = new ArrayList<>();

        for (UpComingEvent object : result) {
            if (DateUtils.isLessThan24(object.getBegin_at(), null)) {
                list.add(object);
            }
        }

        return list;
    }


    public static void save(int event_id, String event_name, String begin_at) {

        Realm realm = Realm.getDefaultInstance();

        UpComingEvent upe = new UpComingEvent();
        upe.setEvent_id(event_id);
        upe.setEvent_name(event_name);
        upe.setBegin_at(begin_at);
        upe.setNotified(false);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(upe);
        realm.commitTransaction();


    }


    public static void remove(final int event_id) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UpComingEvent result = realm.where(UpComingEvent.class).equalTo("event_id", event_id).findFirst();
                if (result != null && result.isLoaded() && result.isValid() && result.getEvent_id() > 0) {
                    result.deleteFromRealm();
                }
            }
        });


    }

    public static void notified() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<UpComingEvent> result = realm.where(UpComingEvent.class).equalTo("notified", false).findAll();

        for (UpComingEvent object : result) {
            if (DateUtils.isLessThan24(object.getBegin_at(), null)) {
                realm.beginTransaction();
                object.setNotified(true);
                realm.copyToRealmOrUpdate(object);
                realm.commitTransaction();
            }
        }


    }

}
