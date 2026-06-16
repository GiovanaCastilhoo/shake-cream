package com.shakecream.model;

import java.util.List;

public class OrderRequest {

    private int userId;
    private int tableId;
    private List<OrderItem> items;

    public OrderRequest() {}

    // =========================
    // GETTERS E SETTERS
    // =========================

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}