package com.microservice.microserviceshoppingcart.domain.models;

public class Item {
    private Long id;
    private Long productId;
    private int quantity;
    private ShoppingCart shoppingCart;

    public Item(Long id, Long productId, int quantity, ShoppingCart shoppingCart) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.shoppingCart = shoppingCart;
    }

    public Item() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
