package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {
     long categoryId;
     String categoryName;
     Date createAt;
     String description;
     byte isDeleted;
     Date updateAt;
     Category category;

    public Category() {
    }

    public Category(long categoryId, String categoryName, Date createAt, String description, byte isDeleted, Date updateAt, Category category) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createAt = createAt;
        this.description = description;
        this.isDeleted = isDeleted;
        this.updateAt = updateAt;
        this.category = category;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
