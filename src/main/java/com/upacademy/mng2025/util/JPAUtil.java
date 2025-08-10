package com.upacademy.mng2025.util;

import com.upacademy.mng2025.entity.Shelf;
import java.util.List;

public interface JPAUtil {
    void insert(Shelf s);
    void update(Shelf s);
    void delete(String id);
    Shelf findById(String id);
    List<Shelf> findAll();
}
