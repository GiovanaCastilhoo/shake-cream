package com.shakecream.app.services;

import com.google.gson.Gson;
import com.shakecream.app.models.Category;
import com.shakecream.app.utils.HttpClientHelper;
import com.shakecream.app.utils.SessionStore;

import java.io.OutputStream;
import java.net.*;
import java.util.*;

public class CategoryService {

    private final Gson gson = new Gson();
    private final String BASE_URL = "http://localhost:4567/categories";

    public List<Category> getCategories() {
        try {
            URL url = new URL("http://localhost:4567/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            Category[] categories = gson.fromJson(response, Category[].class);
            return Arrays.asList(categories);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Category create(Category category) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(category);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            return gson.fromJson(response, Category.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Category update(int id, Category category) {
        try {
            System.out.println("TOKEN NO UPDATE: " + SessionStore.getToken());
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(category);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            Scanner sc = new Scanner(conn.getInputStream());
            String response = sc.useDelimiter("\\A").next();
            sc.close();

            return gson.fromJson(response, Category.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
