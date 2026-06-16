package com.shakecream.dao.additional;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shakecream.model.Additional;

public class AdditionalDAO {

    private Connection conn;

    public AdditionalDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(String name, double price) {
        String sql = "INSERT INTO additionals (name, price) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.setDouble(2, price);

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new RuntimeException("Erro ao obter ID");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR_INSERT_ADDITIONAL", e);
        }
    }

    public List<Additional> findAll() {
        String sql = "SELECT * FROM additionals";

        List<Additional> additionals = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Additional a = new Additional();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setPrice(rs.getDouble("price"));

                additionals.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_FIND_ALL_ADDITIONALS", e);
        }

        return additionals;
    }

    public Additional findById(int id) {
        String sql = "SELECT * FROM additionals WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                System.out.println("ENCONTROU O REGISTRO");
                Additional additional = new Additional();
                additional.setId(rs.getInt("id"));
                additional.setName(rs.getString("name"));
                additional.setPrice(rs.getDouble("price"));

                return additional;
            }

            throw new RuntimeException("ADDITIONAL_NOT_FOUND"); 

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, String name, double price) {
        String sql = "UPDATE additionals SET name = ?, price = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERROR_UPDATE_ADDITIONAL", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM additionals WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_DELETE_ADDITIONAL", e);
        }
    }

    public boolean exists(String name) {
        String sql = "SELECT 1 FROM additionals WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("ERROR_CHECK_ADDITIONAL", e);
        }
    }

    public double findPrice(int id) {

        String sql = "SELECT price FROM additionals WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR_FIND_ADDITIONAL_PRICE", e);
        }

        return 0;
    }
}
