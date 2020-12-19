package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Droideve on 11/13/2017.
 */

public class Review extends RealmObject {

    @PrimaryKey
    private int id_rate;
    private int store_id;
    private double rate;
    private String review;
    private String pseudo;
    private String image;
    private int guest_id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public void setId_rate(int id_rate) {
        this.id_rate = id_rate;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
