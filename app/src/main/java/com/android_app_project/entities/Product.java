package com.android_app_project.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import retrofit2.http.OPTIONS;

public class Product implements Serializable {
    private long productId;

    private String brand;

    private String createAt;

    private String description;

    private String image;

    private byte isActive;

    private byte isDeleted;

    private BigDecimal price;

    private String productName;

    private BigDecimal rating;

    private int sold;

    private String updateAt;

    private List<Color> colors;
    private Category category;
    private List<Size> sizes;
    public Product() {
    }

    public Product(long productId, String brand, String createAt, String description, String image,
                   byte isActive, byte isDeleted, BigDecimal price, String productName, BigDecimal rating,
                   int sold, String updateAt, List<Color> colors, Category category, List<Size> sizes) {
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
        this.colors = colors;
        this.category = category;
        this.sizes = sizes;
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

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }
}
