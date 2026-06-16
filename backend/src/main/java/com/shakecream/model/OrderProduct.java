package com.shakecream.model;

import java.util.List;

public class OrderProduct {

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    private List<Integer> additionals;

    // =========================
    // ADDITIONALS
    // =========================
    public List<Integer> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(List<Integer> additionals) {
        this.additionals = additionals;
    }

    // =========================
    // GETTERS / SETTERS
    // =========================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}