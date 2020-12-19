package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WhichList extends RealmObject {

    @PrimaryKey
    private int id;
    private RealmList<Bookmark> listItems;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Bookmark> getListItems() {
        return listItems;
    }

    public void setListItems(RealmList<Bookmark> listItems) {
        this.listItems = listItems;
    }
}
