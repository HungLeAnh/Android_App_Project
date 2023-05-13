package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Color implements Serializable {

     long colorId;
     Date createAt;
     Date updateAt;
     String value;

    public Color() {
    }

    public Color(long colorId, Date createAt, Date updateAt, String value) {
        this.colorId = colorId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.value = value;
    }

    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
