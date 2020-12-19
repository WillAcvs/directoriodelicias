package com.directoriodelicias.apps.delicias.Services;

/**
 * Created by Droideve on 8/19/2016.
 */

public class BusMessage {

    public static int CLOSE_PANEL = 0;
    public static int SEARCH_ON_SUBMIT = 56;
    public static int GET_NBR_NEW_NOTIFS = 6635;
    private int intValue;
    private int position;
    private int order = CLOSE_PANEL;
    private String terms = "";
    private int type = SEARCH_ON_SUBMIT;

    public int getType() {
        return type;
    }


    @Override
    public String toString() {
        return "BusMessage{" +
                "order=" + order +
                ", terms='" + terms + '\'' +
                ", type=" + type +
                '}';
    }
}
