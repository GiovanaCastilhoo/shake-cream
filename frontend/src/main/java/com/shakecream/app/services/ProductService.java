package com.shakecream.app.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shakecream.app.models.Product;
import com.shakecream.app.utils.HttpClientHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductService {

    private static final String BASE_URL = "http://localhost:4567/products";
    private final Gson gson = new Gson();

    public List<Product> getAll() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return gson.fromJson(reader, new TypeToken<List<Product>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR_GET_PRODUCTS: " + e.getMessage(), e);
        }
    }

    public List<Product> getByCategoryId(int categoryId) {
        try {
            URL url = new URL(BASE_URL + "/category/" + categoryId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return gson.fromJson(
                    reader,
                    new TypeToken<List<Product>>() {
                    }.getType());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR_GET_PRODUCTS_BY_CATEGORY: " + e.getMessage(), e);
        }
    }

    public Product create(Product product) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(product);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return gson.fromJson(reader, Product.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product update(int id, Product product) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            HttpClientHelper.applyJsonHeaders(conn);
            HttpClientHelper.applyAuth(conn);

            String json = gson.toJson(product);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            return gson.fromJson(reader, Product.class);

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

            int responseCode = conn.getResponseCode();

            return responseCode == 200;

        } catch (Exception e) {
            throw new RuntimeException("ERROR_DELETE_PRODUCT", e);
        }
    }
}