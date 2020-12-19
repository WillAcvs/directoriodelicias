package io.realm;


public interface BannerRealmProxyInterface {
    public int realmGet$id();
    public void realmSet$id(int value);
    public String realmGet$title();
    public void realmSet$title(String value);
    public String realmGet$description();
    public void realmSet$description(String value);
    public com.directoriodelicias.apps.delicias.classes.Images realmGet$images();
    public void realmSet$images(com.directoriodelicias.apps.delicias.classes.Images value);
    public String realmGet$module();
    public void realmSet$module(String value);
    public String realmGet$module_id();
    public void realmSet$module_id(String value);
    public int realmGet$status();
    public void realmSet$status(int value);
    public String realmGet$date_start();
    public void realmSet$date_start(String value);
    public String realmGet$date_end();
    public void realmSet$date_end(String value);
    public int realmGet$is_can_expire();
    public void realmSet$is_can_expire(int value);
    public RealmList<com.directoriodelicias.apps.delicias.classes.Images> realmGet$listImages();
    public void realmSet$listImages(RealmList<com.directoriodelicias.apps.delicias.classes.Images> value);
}
