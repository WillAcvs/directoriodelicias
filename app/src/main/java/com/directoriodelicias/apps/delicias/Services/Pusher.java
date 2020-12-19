package com.directoriodelicias.apps.delicias.Services;

/**
 * Created by Droideve on 12/21/2016.
 */

public class Pusher {

    public static int MESSAGE = 1;
    public static int ONLINE = 2;
    public static int OFFLINE = 3;

    public static int USER_CONNECTED = 4;
    public static int MESSAGE_DELIVERED = 5;

    public static int USER_TYPING = 6;
    public static int USER_STOP_TYPING = 7;

    private int type = MESSAGE;
    private String message;

    public Pusher(int type, String msg) {

        message = msg;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Pusher{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
