package com.shakecream.dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.shakecream.model.OrderResponse;

public class OrderDAO {

    private final Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    // =========================
    // CRIAR PEDIDO
    // =========================
    public int createOrder(int userId, int tableId) {

        String sql = """
            INSERT INTO orders (user_id, table_id, status, total_price, created_at, updated_at)
            VALUES (?, ?, 'PENDING', 0, NOW(), NOW())
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, tableId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Falha ao criar pedido.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new RuntimeException("Falha ao obter ID do pedido.");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar pedido: " + e.getMessage(), e);
        }
    }

    // =========================
    // ATUALIZAR TOTAL
    // =========================
    public void updateTotal(int orderId, double total) {

        String sql = """
            UPDATE orders
            SET total_price = ?, updated_at = NOW()
            WHERE id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, total);
            stmt.setInt(2, orderId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pedido não encontrado para atualizar total.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar total: " + e.getMessage(), e);
        }
    }

    public void updateStatus(int orderId, String status) {

        String sql = """
            UPDATE orders
            SET status = ?, updated_at = NOW()
            WHERE id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Pedido não encontrado para atualizar status.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar status: " + e.getMessage(), e);
        }
    }

    public Object getOrderDetails(int orderId) {

    String sqlOrder = """
        SELECT id, status, total_price
        FROM orders
        WHERE id = ?
    """;

    try (PreparedStatement stmt = conn.prepareStatement(sqlOrder)) {

        stmt.setInt(1, orderId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            throw new RuntimeException("Pedido não encontrado.");
        }

        OrderResponse order = new OrderResponse();
        order.setOrderId(rs.getInt("id"));
        order.setStatus(rs.getString("status"));
        order.setTotal(rs.getDouble("total_price"));

        OrderProductDAO orderProductDAO = new OrderProductDAO(conn);

        order.setItems(orderProductDAO.findByOrderId(orderId));

        return order;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage(), e);
        }
    }
}