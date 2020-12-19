package io.realm;


public interface DiscussionRealmProxyInterface {
    public int realmGet$discussionId();
    public void realmSet$discussionId(int value);
    public com.directoriodelicias.apps.delicias.classes.User realmGet$senderUser();
    public void realmSet$senderUser(com.directoriodelicias.apps.delicias.classes.User value);
    public int realmGet$receiverId();
    public void realmSet$receiverId(int value);
    public RealmList<com.directoriodelicias.apps.delicias.classes.Message> realmGet$messages();
    public void realmSet$messages(RealmList<com.directoriodelicias.apps.delicias.classes.Message> value);
    public String realmGet$createdAt();
    public void realmSet$createdAt(String value);
    public int realmGet$status();
    public void realmSet$status(int value);
    public boolean realmGet$isSystem();
    public void realmSet$isSystem(boolean value);
}
