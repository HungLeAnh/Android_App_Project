package com.android_app_project.entities;

import java.io.Serializable;
import java.util.Date;

public class CartItem implements Serializable {

    private long cartItemId;

    private int count;

    private Date createAt;

    private byte isDeleted;

    private Date updateAt;

    private Cart cart;

    private Color color;

    private Product product;

    private Size size;

    public CartItem() {
    }

    public CartItem(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartItem(long cartItemId, int count, Date createAt, byte isDeleted, Date updateAt, Cart cart, Color color, Product product, Size size) {
        this.cartItemId = cartItemId;
        this.count = count;
        this.createAt = createAt;
        this.isDeleted = isDeleted;
        this.updateAt = updateAt;
        this.cart = cart;
        this.color = color;
        this.product = product;
        this.size = size;
    }

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
