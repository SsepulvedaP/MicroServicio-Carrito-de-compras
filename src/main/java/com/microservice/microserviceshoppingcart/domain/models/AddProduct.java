package com.microservice.microserviceshoppingcart.domain.models;

public class AddProduct {
    private Long productId;
    private int quantity;

    public AddProduct(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public AddProduct(){

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
