package com.example.administrator.myprivateframework.entity;

/**
 * Created by Administrator on 2017/8/30.
 */

public class User {
    private String token;

    public User() {
    }

    public User(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
