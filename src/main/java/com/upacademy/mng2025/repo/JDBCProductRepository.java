package com.upacademy.mng2025.repo;

import com.upacademy.mng2025.app.DB;
import com.upacademy.mng2025.entity.Product;
import java.sql.*;
import java.util.*;

public class JDBCProductRepository implements ProductRepository {
    @Override
    public void insert(Product p) throws SQLException {
        String sql = "INSERT INTO products(id, unit_discount, vat_percent, pvp) VALUES(?,?,?,?)";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getId());
            ps.setDouble(2, p.getUnitDiscount());
            ps.setDouble(3, p.getVatPercent());
            ps.setDouble(4, p.getPvp());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Product p) throws SQLException {
        String sql = "UPDATE products SET unit_discount=?, vat_percent=?, pvp=? WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setDouble(1, p.getUnitDiscount());
            ps.setDouble(2, p.getVatPercent());
            ps.setDouble(3, p.getPvp());
            ps.setString(4, p.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Product findById(String id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id=?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return new Product(
                rs.getString("id"),
                rs.getDouble("unit_discount"),
                rs.getDouble("vat_percent"),
                rs.getDouble("pvp")
            );
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement st = DB.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("id"),
                    rs.getDouble("unit_discount"),
                    rs.getDouble("vat_percent"),
                    rs.getDouble("pvp")
                ));
            }
        }
        return list;
    }
}
