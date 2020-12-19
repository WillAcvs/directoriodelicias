package com.directoriodelicias.apps.delicias.controllers.notification;

import com.directoriodelicias.apps.delicias.classes.Notification;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotificationController {


    public static List<Notification> getAllNotification() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Notification.class)
                .findAllSorted("id", Sort.DESCENDING);
        List<Notification> array = new ArrayList<>();
        array.addAll(result.subList(0, result.size()));
        return array;
    }


    public static int countUnseenNotifications() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Notification.class)
                .equalTo("status", 0)
                .equalTo("status", -1).findAll();
        return result != null ? result.size() : 0;
    }


    public static Notification getNotificationDetail(int id) {
        Realm realm = Realm.getDefaultInstance();
        Notification obj = realm.where(Notification.class).equalTo("id", id).findFirst();
        return obj;
    }


    public static boolean insertNotifications(final RealmList<Notification> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Notification notification : list) {
                    realm.copyToRealmOrUpdate(notification);
                }
            }
        });
        return true;
    }

    public static boolean updateNotification(final Notification notification) {

        if (notification != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(notification);

                }
            });
        }

        return true;
    }

    public static boolean insertNotification(final Notification notification) {

        if (notification != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(notification);

                }
            });
        }

        return true;
    }


    public static void removeAll() {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults result = realm.where(Notification.class).findAll();
                result.deleteAllFromRealm();
            }
        });

    }


    public static void removeNotification(final Notification notification, final Realm realm) {

        Notification result = realm.where(Notification.class)
                .equalTo("id", notification.getId())
                .findFirst();
        result.deleteFromRealm();

    }
}
