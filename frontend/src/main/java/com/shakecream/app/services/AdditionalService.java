package com.shakecream.app.services;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.shakecream.app.models.Additional;
import com.shakecream.app.utils.HttpClientHelper;
import com.shakecream.app.utils.SessionStore;

public class AdditionalService {
    private final Gson gson = new Gson();
    private final String BASE_URL = "http://localhost:4567/additionals";

    public List<Additional> getAdditionals() {
        try {
            URL url = new URL("http://localhost:4567/additionals");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            Additional[] additionals = gson.fromJson(response, Additional[].class);
            return Arrays.asList(additionals);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Additional create(Additional additional) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(additional);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            return gson.fromJson(response, Additional.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Additional update(int id, Additional additional) {
        try {
            System.out.println("TOKEN NO UPDATE: " + SessionStore.getToken());
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(additional);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            return gson.fromJson(response, Additional.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("DELETE");

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            return conn.getResponseCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
