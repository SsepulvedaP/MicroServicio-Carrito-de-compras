package com.microservice.microserviceshoppingcart.domain.models;

import java.time.LocalDateTime;

public class CartItems {
    private Long productId;
    private String productName;
    private int quantity;
    private double pricePerUnit;
    private double totalPrice;
    private int stockAvailable;
    private LocalDateTime replenishmentDate;
    private String categoryName;
    private String brandName;


    public CartItems(Long productId, String productName, int quantity, double pricePerUnit, double totalPrice, int stockAvailable, LocalDateTime replenishmentDate, String categoryName, String brandName) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
        this.stockAvailable = stockAvailable;
        this.replenishmentDate = replenishmentDate;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }

    public CartItems() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(int stockAvailable) {
        this.stockAvailable = stockAvailable;
    }

    public LocalDateTime getReplenishmentDate() {
        return replenishmentDate;
    }

    public void setReplenishmentDate(LocalDateTime replenishmentDate) {
        this.replenishmentDate = replenishmentDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
