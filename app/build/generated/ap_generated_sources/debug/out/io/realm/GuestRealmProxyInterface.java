package io.realm;


public interface GuestRealmProxyInterface {
    public int realmGet$id();
    public void realmSet$id(int value);
    public String realmGet$senderId();
    public void realmSet$senderId(String value);
    public String realmGet$fcmId();
    public void realmSet$fcmId(String value);
    public double realmGet$lat();
    public void realmSet$lat(double value);
    public double realmGet$lng();
    public void realmSet$lng(double value);
    public String realmGet$last_activity();
    public void realmSet$last_activity(String value);
}
