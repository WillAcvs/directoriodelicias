package com.directoriodelicias.apps.delicias.classes;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Directorio on 2/12/2016.
 */
public class Store extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private Images images;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private String description;
    private int type;
    private int status;
    private String detail;
    private User user;
    private int user_id;
    private String imageJson;
    private boolean Voted;
    private double votes;
    private String nbr_votes;
    private RealmList<Images> listImages;
    private String phone;
    private int saved;
    private int nbrOffers;
    private String lastOffer;
    private int category_id;
    private int featured;
    private int gallery;
    private String category_name;
    private String category_color;
    private int canChat;
    private int opening;
    private String opening_time_table;
    private RealmList<OpeningTime> opening_time_table_list;
    private String link;

    public RealmList<OpeningTime> getOpening_time_table_list() {
        return opening_time_table_list;
    }

    public void setOpening_time_table_list(RealmList<OpeningTime> opening_time_table_list) {
        this.opening_time_table_list = opening_time_table_list;
    }

    public String getOpening_time_table() {
        return opening_time_table;
    }

    public void setOpening_time_table(String opening_time_table) {
        this.opening_time_table = opening_time_table;
    }

    public int getGallery() {
        return gallery;
    }

    public void setGallery(int gallery) {
        this.gallery = gallery;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getLastOffer() {
        return lastOffer;
    }

    public void setLastOffer(String lastOffer) {
        this.lastOffer = lastOffer;
    }

    public int getNbrOffers() {
        return nbrOffers;
    }

    public void setNbrOffers(int nbrOffers) {
        this.nbrOffers = nbrOffers;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Images> getListImages() {
        return listImages;
    }

    public void setListImages(RealmList<Images> listImages) {
        this.listImages = listImages;
    }

    public boolean isVoted() {
        return Voted;
    }

    public void setVoted(boolean voted) {
        Voted = voted;
    }

    public double getVotes() {
        return votes;
    }

    public void setVotes(double votes) {
        this.votes = votes;
    }

    public String getNbr_votes() {
        return nbr_votes;
    }

    public void setNbr_votes(String nbr_votes) {
        this.nbr_votes = nbr_votes;
    }

    public String getImageJson() {
        return imageJson;
    }

    public void setImageJson(String imageJson) {
        this.imageJson = imageJson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOpening() {
        return opening;
    }

    public void setOpening(int opening) {
        this.opening = opening;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_color() {
        return category_color;
    }

    public void setCategory_color(String category_color) {
        this.category_color = category_color;
    }

    public int getCanChat() {
        return canChat;
    }

    public void setCanChat(int canChat) {
        this.canChat = canChat;
    }

    public static class Detail {
    }

    public static class Tags {

        public static String ID = "id_store";
        public static String NAME = "name";
        public static String ADDRESS = "description";
        public static String LAT = "latitude";
        public static String LONG = "longitude";
        public static String DISTANCE = "distance";
        public static String TYPE = "type";
        public static String STATUS = "status";
        public static String DETAIL = "detail";
        public static String PHONE = "phone";
        public static String VOTES = "votes";
        public static String LISTIMAGES = "ListImages";

    }
}
