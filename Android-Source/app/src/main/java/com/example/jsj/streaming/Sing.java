package com.example.jsj.streaming;

import android.widget.EditText;

/**
 * Created by jsj on 2017-10-26.
 */

public class Sing {

    String id;
    String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sing(String id, String password) {
        this.id = id;

        this.password = password;
    }



}
