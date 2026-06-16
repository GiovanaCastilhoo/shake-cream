package com.shakecream.dao.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDAO {

    private Connection conn;

    public TableDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT
    public void insert(int number) {
        String sql = "INSERT INTO tables (number) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, number);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_INSERT_TABLE", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM tables WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_DELETE_TABLE", e);
        }
    }

    // EXISTS
    public boolean exists(int number) {
        String sql = "SELECT 1 FROM tables WHERE number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, number);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CHECK_TABLE", e);
        }
    }
}