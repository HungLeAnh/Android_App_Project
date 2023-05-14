package com.android_app_project.entities;


import android.text.format.DateFormat;

import com.android_app_project.Utils.Constants;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class Account implements Serializable {

    private long accountId;

    private String createAt;

    private String hashCode;

    private byte isActive;

    private byte isDeleted;

    private String role;

    private String salt;

    private String updateAt;

    private String username;

    private String oneTimePassword;

    private String otpRequestedTime;

    public Account(long accountId, String createAt, String hashCode, byte isActive, byte isDeleted, String role, String salt, String updateAt, String username, String oneTimePassword, String otpRequestedTime) throws ParseException {
        this.accountId = accountId;
        this.createAt = createAt;
        this.hashCode = hashCode;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.role = role;
        this.salt = salt;
        this.updateAt = updateAt;

        this.username = username;
        this.oneTimePassword = oneTimePassword;
        this.otpRequestedTime = otpRequestedTime;
    }

    public Account() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getOtpRequestedTime() {
        return otpRequestedTime;
    }

    public void setOtpRequestedTime(String otpRequestedTime) {
        this.otpRequestedTime = otpRequestedTime;
    }
}
