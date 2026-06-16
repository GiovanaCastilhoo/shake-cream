package com.shakecream.app.services;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.shakecream.app.models.LoginResponse;

public class ClientService {

    private final Gson gson = new Gson();

    public LoginResponse loginClient(String name, int tableNumber) {
        try {
            URL url = new URL("http://localhost:4567/client/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = String.format(
                    "{\"name\":\"%s\",\"tableNumber\":%d}",
                    name, tableNumber);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int status = conn.getResponseCode();

            Scanner sc;

            if (status >= 200 && status < 300) {
                sc = new Scanner(conn.getInputStream());
            } else {
                sc = new Scanner(conn.getErrorStream());
            }

            String response = sc.useDelimiter("\\A").next();
            sc.close();

            conn.disconnect();

            System.out.println("Client login response: " + response);

            if (status < 200 || status >= 300) {
                System.out.println("Erro no login do cliente!");
                return null;
            }

            return gson.fromJson(response, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}