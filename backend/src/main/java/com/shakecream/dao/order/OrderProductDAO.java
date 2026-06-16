package com.shakecream.dao.order;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shakecream.model.OrderProduct;

public class OrderProductDAO {

  private Connection conn;

  public OrderProductDAO(Connection conn) {
    this.conn = conn;
  }

  public int addProduct(int orderId, int productId, int quantity, double price) {

    String sql = """
        INSERT INTO order_products (order_id, product_id, quantity, price)
        VALUES (?, ?, ?, ?)
    """;

    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setInt(1, orderId);
      stmt.setInt(2, productId);
      stmt.setInt(3, quantity);
      stmt.setDouble(4, price);

      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();

      if (rs.next()) {
        return rs.getInt(1);
      }

    } catch (Exception e) {
      throw new RuntimeException("ERROR_ADD_ORDER_PRODUCT", e);
    }

    return -1;
  }

  // =========================
  // LISTAR ITENS DO PEDIDO
  // =========================
  public List<OrderProduct> findByOrderId(int orderId) {

    String sql = """
        SELECT 
            op.id,
            op.order_id,
            op.product_id,
            op.quantity,
            op.price,
            p.name AS product_name
        FROM order_products op
        JOIN products p ON p.id = op.product_id
        WHERE op.order_id = ?
    """;

    List<OrderProduct> list = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, orderId);

      try (ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

          OrderProduct op = new OrderProduct();

          op.setId(rs.getInt("id"));
          op.setOrderId(rs.getInt("order_id"));
          op.setProductId(rs.getInt("product_id"));
          op.setQuantity(rs.getInt("quantity"));
          op.setPrice(rs.getDouble("price"));

          list.add(op);
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("ERROR_FIND_ORDER_PRODUCTS", e);
    }

    // =========================
    // ADDITIONALS (aninhado)
    // =========================
    OrderProductAdditionalDAO additionalDAO = new OrderProductAdditionalDAO(conn);

    for (OrderProduct op : list) {
      op.setAdditionals(
          additionalDAO.findAdditionalsByOrderProduct(op.getId())
      );
    }

    return list;
  }
}