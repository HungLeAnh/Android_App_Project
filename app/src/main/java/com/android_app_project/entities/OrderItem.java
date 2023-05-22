package com.android_app_project.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {
    private long orderItemId;

    private int count;

    private BigDecimal discount;

    private BigDecimal subtotal;

    private Color color;

    private Size size;

    private Order order;

    private Product product;

    public OrderItem() {
    }

    public OrderItem(long orderItemId, int count, BigDecimal discount, BigDecimal subtotal, Color color, Size size, Order order, Product product) {
        this.orderItemId = orderItemId;
        this.count = count;
        this.discount = discount;
        this.subtotal = subtotal;
        this.color = color;
        this.size = size;
        this.order = order;
        this.product = product;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
