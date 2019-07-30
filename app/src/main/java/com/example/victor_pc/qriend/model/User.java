package com.example.victor_pc.qriend.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.victor_pc.qriend.BR;

import java.util.List;

public class User extends BaseObservable {

    private String username;
    private String password;
    private String qr_code;
    private String user_key;
    private List<Friend> friendList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
        return this;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
        return this;
    }

    @Bindable
    public List<Friend> getFriendList() {
        return friendList;
    }

    public User setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
        notifyPropertyChanged(BR.friendList);
        return this;
    }

    @Bindable
    public String getQr_code() {
        return qr_code;
    }

    public User setQr_code(String qr_code) {
        this.qr_code = qr_code;
        notifyPropertyChanged(BR.qr_code);
        return this;
    }

    @Bindable
    public String getUser_key() {
        return user_key;
    }

    public User setUser_key(String user_key) {
        this.user_key = user_key;
        notifyPropertyChanged(BR.user_key);
        return this;
    }
}
