package com.upacademy.mng2025.repo;

import com.upacademy.mng2025.app.DB;
import com.upacademy.mng2025.entity.Shelf;
import java.sql.*;
import java.util.*;

public class JDBCShelfRepository implements ShelfRepository {
    @Override
    public void insert(Shelf s) throws SQLException {
        String sql = "INSERT INTO shelves(id, capacity, daily_rent_price, product_id) VALUES(?,?,?,?)";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getId());
            ps.setInt(2, s.getCapacity());
            ps.setDouble(3, s.getDailyRentPrice());
            if (s.getProductId() == null || s.getProductId().trim().isEmpty()) {
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(4, s.getProductId());
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Shelf s) throws SQLException {
        String sql = "UPDATE shelves SET capacity=?, daily_rent_price=?, product_id=? WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setInt(1, s.getCapacity());
            ps.setDouble(2, s.getDailyRentPrice());
            if (s.getProductId() == null || s.getProductId().trim().isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(3, s.getProductId());
            }
            ps.setString(4, s.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM shelves WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Shelf findById(String id) throws SQLException {
        String sql = "SELECT * FROM shelves WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return new Shelf(
                rs.getString("id"),
                rs.getInt("capacity"),
                rs.getDouble("daily_rent_price"),
                rs.getString("product_id")
            );
        }
    }

    @Override
    public List<Shelf> findAll() throws SQLException {
        List<Shelf> list = new ArrayList<>();
        String sql = "SELECT * FROM shelves";
        try (Statement st = DB.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Shelf(
                    rs.getString("id"),
                    rs.getInt("capacity"),
                    rs.getDouble("daily_rent_price"),
                    rs.getString("product_id")
                ));
            }
        }
        return list;
    }
}
