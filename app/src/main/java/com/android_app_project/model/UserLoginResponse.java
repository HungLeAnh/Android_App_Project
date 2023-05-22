package com.android_app_project.model;

import java.io.Serializable;

public class UserLoginResponse implements Serializable {
    private String role;
    private String token;

    public UserLoginResponse(String role, String token) {
        this.role = role;
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
