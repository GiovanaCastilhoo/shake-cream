package com.shakecream.app.utils;

public class SessionStore {

    private static String token;
    private static String role;
    private static Integer userId;
    private static Integer tableNumber;
    private static String sessionId;

    public static void setSessionId(String sessionId) {
        SessionStore.sessionId = sessionId;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setToken(String token) {
        SessionStore.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static void setRole(String role) {
        SessionStore.role = role;
    }

    public static String getRole() {
        return role;
    }

    public static void setUserId(Integer userId) {
        SessionStore.userId = userId;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setTableNumber(Integer tableNumber) {
        SessionStore.tableNumber = tableNumber;
    }

    public static Integer getTableNumber() {
        return tableNumber;
    }

    public static void clear() {
        token = null;
        role = null;
        userId = null;
        tableNumber = null;
    }
}