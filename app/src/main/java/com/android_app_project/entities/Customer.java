package com.android_app_project.entities;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {

    private long customerId;

    private String avatar;

    private Date createAt;

    private String eWallet;

    private String email;

    private String firstName;

    private byte isDeleted;

    private String lastName;

    private String phone;

    private Date updateAt;

    Account account;

    public Customer() {
    }

    public Customer(long customerId, String avatar, Date createAt, String eWallet, String email,
                    String firstName, byte isDeleted, String lastName, String phone, Date updateAt,Account account) {
        this.customerId = customerId;
        this.avatar = avatar;
        this.createAt = createAt;
        this.eWallet = eWallet;
        this.email = email;
        this.firstName = firstName;
        this.isDeleted = isDeleted;
        this.lastName = lastName;
        this.phone = phone;
        this.updateAt = updateAt;
        this.account = account;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String geteWallet() {
        return eWallet;
    }

    public void seteWallet(String eWallet) {
        this.eWallet = eWallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
