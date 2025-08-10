package com.upacademy.mng2025.repo;

import com.upacademy.mng2025.entity.Shelf;
import java.sql.SQLException;
import java.util.List;

public interface ShelfRepository {
    void insert(Shelf s) throws SQLException;
    void update(Shelf s) throws SQLException;
    void delete(String id) throws SQLException;
    Shelf findById(String id) throws SQLException;
    List<Shelf> findAll() throws SQLException;
}
