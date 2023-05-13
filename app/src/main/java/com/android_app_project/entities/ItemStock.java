package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;

public class ItemStock implements Serializable {

    private long itemStockId;

    private int count;

    private Date createAt;

    private Date updateAt;

    private Color color;

    private Product product;

    private Size size;

    public ItemStock() {
    }

    public ItemStock(long itemStockId, int count, Date createAt, Date updateAt, Color color, Product product, Size size) {
        this.itemStockId = itemStockId;
        this.count = count;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.color = color;
        this.product = product;
        this.size = size;
    }

    public long getItemStockId() {
        return itemStockId;
    }

    public void setItemStockId(long itemStockId) {
        this.itemStockId = itemStockId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
