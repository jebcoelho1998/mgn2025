package com.upacademy.mng2025.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shelf {
    private String id;
    private int capacity;
    private double dailyRentPrice;
    private String productId;

    public Shelf() {} 

    public Shelf(String id, int capacity, double dailyRentPrice, String productId) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id inválido");
        }
        this.id = id;
        this.capacity = capacity;
        this.dailyRentPrice = dailyRentPrice;
        this.productId = productId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getDailyRentPrice() { return dailyRentPrice; }
    public void setDailyRentPrice(double dailyRentPrice) { this.dailyRentPrice = dailyRentPrice; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    @Override
    public String toString() {
        return "Shelf{id='" + id + "', capacity=" + capacity + ", dailyRentPrice=" + dailyRentPrice + ", productId='" + productId + "'}";
    }
}
