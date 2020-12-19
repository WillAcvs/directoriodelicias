package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Directorio on 1/5/2016.
 */
public class Inbox extends RealmObject {

    @PrimaryKey
    private String discussionId;
    private User senderUser;
    private int receiverId;
    private RealmList<Message> messages;
    private String createdAt;
    @Ignore
    private int nbrMessage = 0;
    private int status;
    private boolean isSystem = false;

    public static class Tags {
    }


}
