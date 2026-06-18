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

        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        if (sessionId != null && !sessionId.isEmpty()) {
            conn.setRequestProperty("Session-Id", sessionId);
        }

        if ((token == null || token.isEmpty()) && (sessionId == null || sessionId.isEmpty())) {
            System.out.println("No auth or session headers set");
        }
    }
}
