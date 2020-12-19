package com.directoriodelicias.apps.delicias.classes;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

/**
 * Created by Directorio on 12/13/2015.
 */
public class Item {

    public final static String TAG_NAME = "item";
    protected String type;
    private boolean enabled = true;
    private int ID;
    private String Name;
    private String Discription;
    private String ImageUrl;
    private int ImageId;
    private int notify;
    private MaterialDrawableBuilder.IconValue iconDraw;

    public Item() {
        this.type = TAG_NAME;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public MaterialDrawableBuilder.IconValue getIconDraw() {
        return iconDraw;
    }

    public void setIconDraw(MaterialDrawableBuilder.IconValue iconDraw) {
        this.iconDraw = iconDraw;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
