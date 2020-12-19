package com.directoriodelicias.apps.delicias.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleImage implements Serializable {


    private String id;
    private String largeUrl;
    private String smallUrl;
    private ArrayList<SimpleImage> listImages;
    private int height;
    private int width;

    public static ArrayList<SimpleImage> convertToSimpleImage(List<Images> realmList) {

        ArrayList<SimpleImage> newImages = new ArrayList<SimpleImage>();

        for (Images image : realmList) {
            SimpleImage si = new SimpleImage();
            String imageId = image.getUrl100_100();
            String imageLarge = image.getUrlFull();
            String imageSmall = image.getUrl100_100();

            si.setId(imageId);
            si.setLargeUrl(imageLarge);
            si.setSmallUrl(imageSmall);
            si.setHeight(image.getHeight());
            si.setWidth(image.getWidth());

            newImages.add(si);
        }

        return newImages;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public ArrayList<SimpleImage> getListImages() {
        return listImages;
    }

    public void setListImages(ArrayList<SimpleImage> listImages) {
        this.listImages = listImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

}
