package com.shakecream.app.services;

import com.google.gson.Gson;
import com.shakecream.app.models.LoginResponse;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AuthService {

    private final String BASE_URL = "http://localhost:4567";
    private final Gson gson = new Gson();

    public LoginResponse login(String email, String password) {
        try {
            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // body JSON
            String json = gson.toJson(new LoginRequest(email, password));

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            return gson.fromJson(response, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}