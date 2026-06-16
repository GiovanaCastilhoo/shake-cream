package com.shakecream.model;

import java.util.List;

public class OrderItem {

    private int productId;
    private int quantity;
    private double price;
    private List<Integer> additionals;

    public OrderItem() {}

    // =========================
    // GETTERS E SETTERS
    // =========================

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(List<Integer> additionals) {
        this.additionals = additionals;
    }
}