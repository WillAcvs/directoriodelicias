package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Directorio on 1/5/2016.
 */
public class Discussion extends RealmObject {


    @Ignore
    public static int DISCUSION_WITH_USER = 120;
    @PrimaryKey
    private int discussionId;
    private User senderUser;
    private int receiverId;
    private RealmList<Message> messages;
    private String createdAt;
    @Ignore
    private int nbrMessage = 0;
    private int status;
    private boolean isSystem = false;

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public int getNbrMessage() {
        return nbrMessage;
    }

    public void setNbrMessage(int nbrMessage) {
        this.nbrMessage = nbrMessage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public RealmList<Message> getMessages() {
        return messages;
    }

    public void setMessages(RealmList<Message> messages) {
        this.messages = messages;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Tags {
    }
}
