package com.shakecream.app.models;

public class ClientLoginResponse {

  private String sessionId;
  private String name;
  private int tableNumber;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

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