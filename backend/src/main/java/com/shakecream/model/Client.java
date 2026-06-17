package com.shakecream.model;

public class Client {

    private String name;
    private int tableNumber;

    public Client(String name, int tableNumber) {
        this.name = name;
        this.tableNumber = tableNumber;
    }

    public String getName() {
        return name;
    }

    public int getTableNumber() {
        return tableNumber;
    }
}