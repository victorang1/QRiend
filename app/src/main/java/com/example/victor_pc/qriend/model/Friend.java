package com.example.victor_pc.qriend.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.example.victor_pc.qriend.BR;

import java.io.Serializable;

public class Friend extends BaseObservable implements Serializable {

    private String username;
    private String key;

    public Friend() {
    }

    public Friend(String username, String key) {
        this.username = username;
        this.key = key;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public Friend setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
        return this;
    }

    @Bindable
    public String getKey() {
        return key;
    }

    public Friend setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
        return this;
    }
}
