package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SavedStores extends RealmObject {


    @PrimaryKey
    private int id = 1;
    private RealmList<Integer> listID;


    public SavedStores() {
        listID = new RealmList<>();
        id = 1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Integer> getListID() {
        return listID;
    }


    public boolean isExist(int id) {

        if (listID != null)
            for (int i = 0; i < listID.size(); i++) {
                if (listID.get(i) == id) {
                    return true;
                }
            }

        return false;
    }

    public void delete(int id) {

        if (listID != null)
            for (int i = 0; i < listID.size(); i++) {
                if (listID.get(i) == id) {
                    listID.remove(i);
                    break;
                }
            }

    }
}
