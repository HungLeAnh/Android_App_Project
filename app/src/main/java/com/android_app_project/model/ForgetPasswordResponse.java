package com.android_app_project.model;

import com.android_app_project.entities.Account;

import java.io.Serializable;

public class ForgetPasswordResponse implements Serializable {
    String message;
    Account account;

    public ForgetPasswordResponse() {
    }

    public ForgetPasswordResponse(String message, Account account) {
        this.message = message;
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
