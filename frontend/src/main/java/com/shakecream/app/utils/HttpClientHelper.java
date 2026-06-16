package com.shakecream.app.utils;

import java.net.HttpURLConnection;

public class HttpClientHelper {

    public static void applyJsonHeaders(HttpURLConnection conn) {
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
    }

    public static void applyAuth(HttpURLConnection conn) {
        String token = SessionStore.getToken();

        System.out.println("TOKEN SEND DEBUG: " + token);

        if (token != null && !token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
            System.out.println("AUTH HEADER SET");
        } else {
            System.out.println("NO TOKEN FOUND");
        }
    }
}
