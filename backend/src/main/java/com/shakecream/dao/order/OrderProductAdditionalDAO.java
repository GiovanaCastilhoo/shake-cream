package com.shakecream.dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdditionalDAO {

    private Connection conn;

    public OrderProductAdditionalDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addAdditional(int orderProductId, int additionalId) {

        String sql = """
            INSERT INTO order_product_additional (order_product_id, additional_id)
            VALUES (?, ?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderProductId);
            stmt.setInt(2, additionalId);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar adicional: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // BUSCAR ADICIONAIS DO ITEM
    // =========================
    public List<Integer> findAdditionalsByOrderProduct(int orderProductId) {

        String sql = """
            SELECT additional_id
            FROM order_product_additional
            WHERE order_product_id = ?
        """;

        List<Integer> list = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderProductId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(rs.getInt("additional_id"));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR_FIND_ORDER_PRODUCT_ADDITIONALS", e);
        }

        return list;
    }
}