package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Color implements Serializable {

     long colorId;
     String createAt;
     String updateAt;
     String value;

    public Color() {
    }

    public Color(long colorId, String createAt, String updateAt, String value) {
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
