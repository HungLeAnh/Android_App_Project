package com.android_app_project.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    private long orderId;
    private String address;
    private String createAt;
    private String description;
    private byte isPaidBefore;
    private String notification;
    private String status;
    private BigDecimal totalPrice;
    private String updateAt;
    private Customer customer;

    public Order() {
    }

    public Order(long orderId, String address, String createAt, String description, byte isPaidBefore, String notification, String status, BigDecimal totalPrice, String updateAt, Customer customer) {
        this.orderId = orderId;
        this.address = address;
        this.createAt = createAt;
        this.description = description;
        this.isPaidBefore = isPaidBefore;
        this.notification = notification;
        this.status = status;
        this.totalPrice = totalPrice;
        this.updateAt = updateAt;
        this.customer = customer;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getIsPaidBefore() {
        return isPaidBefore;
    }

    public void setIsPaidBefore(byte isPaidBefore) {
        this.isPaidBefore = isPaidBefore;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
