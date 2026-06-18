package com.shakecream.app.utils;

import java.net.HttpURLConnection;

public class HttpClientHelper {

    public static void applyJsonHeaders(HttpURLConnection conn) {
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
    }

    public static void applyAuth(HttpURLConnection conn) {
        String token = SessionStore.getToken();
        String sessionId = SessionStore.getSessionId();

        System.out.println("TOKEN SEND DEBUG: " + token);
        System.out.println("SESSION ID SEND DEBUG: " + sessionId);

        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
            System.out.println("AUTH HEADER SET");
        }

        if (sessionId != null && !sessionId.isEmpty()) {
            conn.setRequestProperty("Session-Id", sessionId);
            System.out.println("SESSION HEADER SET");
        }

        if ((token == null || token.isEmpty()) && (sessionId == null || sessionId.isEmpty())) {
            System.out.println("NO AUTH FOUND");
        }
    }
}
