package com.shakecream.dao.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shakecream.model.Product;

public class MenuDAO {
  private Connection conn;

  public MenuDAO(Connection conn) {
    this.conn = conn;
  }

  public void insert(String name) {
    String sql = "INSERT INTO menu (name) VALUES (?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, name);
      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_CREATE_MENU", e);
    }
  }

  public void delete(int id) {
    String sql = "DELETE FROM menu WHERE id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_DELETE_MENU", e);
    }
  }

  public boolean exists(String name) {
    String sql = "SELECT 1 FROM menu WHERE name = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, name);

      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_CHECK_MENU", e);
    }
  }

  public void addProductToMenu(int menuId, int productId) {
    String sql = "INSERT INTO menu_products (menu_id, product_id) VALUES (?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, menuId);
      stmt.setInt(2, productId);
      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_ADD_PRODUCT_TO_MENU", e);
    }
  }

  // REMOVE PRODUCT FROM MENU
  public void removeProduct(int menuId, int productId) {
    String sql = "DELETE FROM menu_products WHERE menu_id = ? AND product_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, menuId);
      stmt.setInt(2, productId);
      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_REMOVE_PRODUCT_FROM_MENU", e);
    }
  }

  public List<Product> findProductsByMenuId(int menuId) {
    String sql = """
            SELECT p.*
            FROM products p
            JOIN menu_products mp ON p.id = mp.product_id
            WHERE mp.menu_id = ?
        """;

    List<Product> list = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, menuId);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Product p = new Product();
          p.setId(rs.getInt("id"));
          p.setName(rs.getString("name"));
          p.setPrice(rs.getDouble("price"));

          list.add(p);
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_FIND_MENU_PRODUCTS", e);
    }

    return list;
  }
}
