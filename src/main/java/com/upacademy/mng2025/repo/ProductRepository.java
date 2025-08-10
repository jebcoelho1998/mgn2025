package com.upacademy.mng2025.repo;

import com.upacademy.mng2025.entity.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    void insert(Product p) throws SQLException;
    void update(Product p) throws SQLException;
    void delete(String id) throws SQLException;
    Product findById(String id) throws SQLException;
    List<Product> findAll() throws SQLException;
}
