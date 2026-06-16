package com.shakecream.model;

public class ClientLoginRequest {

    private String name;
    private int tableNumber;

    public ClientLoginRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
