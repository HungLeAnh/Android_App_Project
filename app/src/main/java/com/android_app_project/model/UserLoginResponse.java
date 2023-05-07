package com.android_app_project.model;

import java.io.Serializable;

public class UserLoginResponse implements Serializable {
    private String JWTtoken;

    public UserLoginResponse(String JWTtoken) {
        this.JWTtoken = JWTtoken;
    }

    public String getJWTtoken() {
        return JWTtoken;
    }

    public void setJWTtoken(String JWTtoken) {
        this.JWTtoken = JWTtoken;
    }


}
