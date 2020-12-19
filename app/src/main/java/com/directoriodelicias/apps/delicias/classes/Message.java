package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Droideve on 1/5/2016.
 */
public class Message extends RealmObject {


    //FROM
    @Ignore
    public final static int SENDER_VIEW = 0;
    @Ignore
    public final static int RECEIVER_VIEW = 1;
    @Ignore
    public final static int LOADING_VIEW = -1;
    //status value
    @Ignore
    public final static int ERROR = -2;
    @Ignore
    public final static int NEW = -1;
    @Ignore
    public final static int SENT = 2;
    @Ignore
    public final static int NO_SENT = 3;
    @PrimaryKey
    private String messageid;
    private String message;
    private String date;
    private int type = SENDER_VIEW;
    private int status = NEW;
    private int discussionId;
    private int senderId;
    private int receiver_id;

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class Tags {


    }


}
