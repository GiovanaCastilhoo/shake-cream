package com.shakecream.dao.product;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shakecream.model.Product;

public class ProductDAO {

    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public int create(Product p) {
        String sql = "INSERT INTO products (name, description, price, image_url, category_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setString(4, p.getImageUrl());
            stmt.setInt(5, p.getCategoryId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new RuntimeException("Erro ao obter ID");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR_CREATE_PRODUCT", e);
        }
    }

    // READ ALL
    public List<Product> findAll() {
        String sql = """
                SELECT
                    p.id,
                    p.name,
                    p.description,
                    p.price,
                    p.image_url,
                    p.category_id,
                    c.name AS category_name
                FROM products p
                JOIN categories c ON c.id = p.category_id
                """;

        List<Product> products = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));

                products.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_ALL_PRODUCTS", e);
        }

        return products;
    }

    // FIND BY ID
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setCategoryId(rs.getInt("category_id"));

                    return p;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_PRODUCT_BY_ID", e);
        }

        return null;
    }

    public void update(Product p) {

        String sql = "UPDATE products SET " +
                "name = ?, " +
                "description = ?, " +
                "price = ?, " +
                "image_url = ?, " +
                "category_id = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setString(4, p.getImageUrl());
            stmt.setInt(5, p.getCategoryId());
            stmt.setInt(6, p.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("PRODUCT_NOT_FOUND");
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_UPDATE_PRODUCT", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_DELETE_PRODUCT", e);
        }
    }
}