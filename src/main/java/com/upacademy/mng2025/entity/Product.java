package com.upacademy.mng2025.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String id;
    private double unitDiscount;
    private double vatPercent;
    private double pvp;

    public Product() {} 

    public Product(String id, double unitDiscount, double vatPercent, double pvp) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id inválido");
        }
        this.id = id;
        this.unitDiscount = unitDiscount;
        this.vatPercent = vatPercent;
        this.pvp = pvp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getUnitDiscount() { return unitDiscount; }
    public void setUnitDiscount(double unitDiscount) { this.unitDiscount = unitDiscount; }

    public double getVatPercent() { return vatPercent; }
    public void setVatPercent(double vatPercent) { this.vatPercent = vatPercent; }

    public double getPvp() { return pvp; }
    public void setPvp(double pvp) { this.pvp = pvp; }

    @Override
    public String toString() {
        return "Product{id='" + id + "', unitDiscount=" + unitDiscount + ", vatPercent=" + vatPercent + ", pvp=" + pvp + "}";
    }
}



