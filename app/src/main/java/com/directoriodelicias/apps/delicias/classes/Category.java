package com.directoriodelicias.apps.delicias.classes;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by idriss on 02/07/2016.
 */

public class Category extends RealmObject implements Serializable {

    @PrimaryKey
    private int numCat;
    private int type;
    private String nameCat;
    private int parentCategory;
    private Images logo;
    private int nbr_stores;
    private String color;
    private int _order;
    private int icon;
    private Images images;
    private boolean menu = true;
    @Ignore
    private Drawable drawableIcon;

    public Category(int numCat, String nameCat, int parentCategory, int icon) {
        this.numCat = numCat;
        this.nameCat = nameCat;
        this.parentCategory = parentCategory;
        this.icon = icon;
        this.type = numCat;
        this.menu = true;
    }

    public Category(int numCat, String nameCat, int parentCategory, Drawable icon) {
        this.numCat = numCat;
        this.nameCat = nameCat;
        this.parentCategory = parentCategory;
        this.drawableIcon = icon;
        this.type = numCat;
        this.icon = 0;
        this.menu = true;
    }
    public Category() {

    }

    public int getNumCat() {
        return numCat;
    }

    public void setNumCat(int numCat) {
        this.numCat = numCat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public int getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(int parentCategory) {
        this.parentCategory = parentCategory;
    }


    public int getNbr_stores() {
        return nbr_stores;
    }

    public void setNbr_stores(int nbr_stores) {
        this.nbr_stores = nbr_stores;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int get_order() {
        return _order;
    }

    public void set_order(int _order) {
        this._order = _order;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Drawable getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public Images getLogo() {
        return logo;
    }

    public void setLogo(Images logo) {
        this.logo = logo;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

