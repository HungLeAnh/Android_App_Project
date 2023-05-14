package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;

public class Size implements Serializable {

    private long sizeId;

    private String createAt;

    private String updateAt;

    private String value;

    public Size() {
    }

    public Size(long sizeId, String createAt, String updateAt, String value) {
        this.sizeId = sizeId;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.value = value;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
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
