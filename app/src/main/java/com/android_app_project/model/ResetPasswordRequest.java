package com.android_app_project.model;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {
    private String code;
    private String password;
    private String repeatPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String code, String password, String repeatPassword) {
        this.code = code;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
