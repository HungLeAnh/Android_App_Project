package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;

public class Size implements Serializable {

    private long sizeId;

    private Date createAt;

    private Date updateAt;

    private String value;

    public Size() {
    }

    public Size(long sizeId, Date createAt, Date updateAt, String value) {
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
