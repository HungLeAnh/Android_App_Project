package com.android_app_project.model;

import java.io.Serializable;

public class VerifyEmailResponse implements Serializable {
    String message;

    public VerifyEmailResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
