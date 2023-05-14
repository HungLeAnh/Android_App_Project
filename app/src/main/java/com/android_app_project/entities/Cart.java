package com.android_app_project.entities;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {

    private long cartId;

    private int countUniqueItems;

    private String createAt;

    private byte isDeleted;

    private String updateAt;

    private Customer customer;

    public Cart() {
    }

    public Cart(long cartId, int countUniqueItems, String createAt, byte isDeleted, String updateAt, Customer customer) {
        this.cartId = cartId;
        this.countUniqueItems = countUniqueItems;
        this.createAt = createAt;
        this.isDeleted = isDeleted;
        this.updateAt = updateAt;
        this.customer = customer;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public int getCountUniqueItems() {
        return countUniqueItems;
    }

    public void setCountUniqueItems(int countUniqueItems) {
        this.countUniqueItems = countUniqueItems;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
