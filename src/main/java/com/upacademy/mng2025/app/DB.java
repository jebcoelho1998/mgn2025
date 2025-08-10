package com.upacademy.mng2025.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/stockdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            openIfNeeded();
            try (Statement st = conn.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS products (" +
                        "id VARCHAR(50) PRIMARY KEY, " +
                        "unit_discount DOUBLE, " +
                        "vat_percent DOUBLE, " +
                        "pvp DOUBLE)");

                st.execute("CREATE TABLE IF NOT EXISTS shelves (" +
                        "id VARCHAR(50) PRIMARY KEY, " +
                        "capacity INT, " +
                        "daily_rent_price DOUBLE, " +
                        "product_id VARCHAR(50), " +
                        "FOREIGN KEY(product_id) REFERENCES products(id))");
            }
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver JDBC MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Erro ao inicializar ligação à BD: " + e.getMessage());
        }
    }

    private static void openIfNeeded() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static Connection getConnection() throws SQLException {
        openIfNeeded();
        return conn;
    }
}
