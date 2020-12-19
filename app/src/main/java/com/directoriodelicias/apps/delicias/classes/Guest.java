package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Droideve on 11/13/2017.
 */

public class Guest extends RealmObject {

    @PrimaryKey
    private int id;
    private String senderId;
    private String fcmId;
    private double lat;
    private double lng;
    private String last_activity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
