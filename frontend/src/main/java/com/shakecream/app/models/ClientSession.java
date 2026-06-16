package com.shakecream.app.models;

public class ClientSession {

    private String token;

    private int userId;
    private String name;
    private String role;

    private int tableNumber;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
}