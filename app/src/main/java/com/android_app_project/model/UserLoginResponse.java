package com.android_app_project.model;

public class UserLoginResponse {
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
