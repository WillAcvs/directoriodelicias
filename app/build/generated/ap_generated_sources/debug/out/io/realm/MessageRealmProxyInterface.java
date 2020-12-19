package io.realm;


public interface MessageRealmProxyInterface {
    public String realmGet$messageid();
    public void realmSet$messageid(String value);
    public String realmGet$message();
    public void realmSet$message(String value);
    public String realmGet$date();
    public void realmSet$date(String value);
    public int realmGet$type();
    public void realmSet$type(int value);
    public int realmGet$status();
    public void realmSet$status(int value);
    public int realmGet$discussionId();
    public void realmSet$discussionId(int value);
    public int realmGet$senderId();
    public void realmSet$senderId(int value);
    public int realmGet$receiver_id();
    public void realmSet$receiver_id(int value);
}
