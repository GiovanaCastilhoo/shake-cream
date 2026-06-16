package com.shakecream.dao.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shakecream.model.Category;

public class CategoryDAO {

    private Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_INSERT_CATEGORY", e);
        }
    }

    public Category findByName(String name) {

        String sql = "SELECT id, name FROM categories WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    return c;
                }

                throw new RuntimeException("CATEGORY_NOT_FOUND");
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_CATEGORY", e);
        }
    }

    public Category findById(int id) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    return c;
                }

                throw new RuntimeException("CATEGORY_NOT_FOUND");
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_CATEGORY", e);
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";

        List<Category> categories = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));

                categories.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_ALL_CATEGORIES", e);
        }

        return categories;
    }

    public boolean exists(String name) {
        String sql = "SELECT 1 FROM categories WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CHECK_CATEGORY", e);
        }
    }

    public void update(int id, String name) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_UPDATE_CATEGORY", e);
        }
    }
}
