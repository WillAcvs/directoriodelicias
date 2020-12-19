package com.directoriodelicias.apps.delicias.classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Directorio on 8/1/2016.
 */

public class Session extends RealmObject {


    @PrimaryKey
    private int sessionId;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

}
