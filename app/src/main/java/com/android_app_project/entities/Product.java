package com.android_app_project.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Product {
    private long productId;
    private String brand;
    private Date createAt;
    private String description;
    private String image;
    private byte isActive;
    private byte isDeleted;
    private BigDecimal price;
    private String productName;
    private BigDecimal rating;
    private int sold;
    private Date updateAt;

    public Product(String image, BigDecimal price, String productName) {
        this.image = image;
        this.price = price;
        this.productName = productName;
    }

    public Product() {
    }

    public Product(long productId, String brand, Date createAt, String description, String image, byte isActive, byte isDeleted,
                   BigDecimal price, String productName, BigDecimal rating, int sold, Date updateAt) {
        this.productId = productId;
        this.brand = brand;
        this.createAt = createAt;
        this.description = description;
        this.image = image;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.price = price;
        this.productName = productName;
        this.rating = rating;
        this.sold = sold;
        this.updateAt = updateAt;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
