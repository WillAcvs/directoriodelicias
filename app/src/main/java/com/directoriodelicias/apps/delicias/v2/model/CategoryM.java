package com.directoriodelicias.apps.delicias.v2.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.directoriodelicias.apps.delicias.R;
import com.directoriodelicias.apps.delicias.classes.Images;
import com.google.gson.annotations.SerializedName;

public class CategoryM {

    private int num;
    private String name;
    private int icon = 0;
    private int nbr_stores;
    private Images images;

    @SerializedName("image")
    private String imgUrl;

    // important code for showLoading image here
    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                /* .setDefaultRequestOptions(new RequestOptions()
                         .circleCrop())*/
                .load(imageURL)
                .placeholder(R.drawable.def_logo)
                .into(imageView);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getNbr_stores() {
        return nbr_stores;
    }

    public void setNbr_stores(int nbr_stores) {
        this.nbr_stores = nbr_stores;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getImgUrl() {
        return this.images != null ? this.images.getUrl100_100() : null;
    }
}
