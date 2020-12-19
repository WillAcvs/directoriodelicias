package com.directoriodelicias.apps.delicias.controllers;

import com.directoriodelicias.apps.delicias.classes.Bookmark;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Droideve on 7/12/2017.
 */

public class BookmarkController {

    public static List<Bookmark> getAllBookmarks() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults result = realm.where(Bookmark.class)
                .findAllSorted("id", Sort.DESCENDING);
        List<Bookmark> array = new ArrayList<>();
        array.addAll(result.subList(0, result.size()));
        return array;
    }


    public static Bookmark getBookmark(int id) {
        Realm realm = Realm.getDefaultInstance();
        Bookmark obj = realm.where(Bookmark.class).equalTo("id", id).findFirst();
        return obj;
    }


    public static boolean insertBookmarks(final RealmList<Bookmark> list) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Bookmark bookmark : list) {
                    realm.copyToRealmOrUpdate(bookmark);
                }
            }
        });
        return true;
    }

    public static boolean updateBookmark(final Bookmark bookmark) {

        if (bookmark != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(bookmark);

                }
            });
        }

        return true;
    }

    public static boolean insertBookmark(final Bookmark bookmark) {

        if (bookmark != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(bookmark);

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
                RealmResults result = realm.where(Bookmark.class).findAll();
                result.deleteAllFromRealm();
            }
        });

    }


    public static void removeBookmark(final Bookmark bookmark, final Realm realm) {

        Bookmark result = realm.where(Bookmark.class)
                .equalTo("id", bookmark.getId())
                .findFirst();
        result.deleteFromRealm();

    }
}
