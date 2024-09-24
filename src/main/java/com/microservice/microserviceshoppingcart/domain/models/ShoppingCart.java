package com.microservice.microserviceshoppingcart.domain.models;


import java.time.LocalDateTime;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private Long userId;
    private LocalDateTime actualizationDate;
    private LocalDateTime creationDate;
    private List<Item> items;

    public ShoppingCart(Long id, Long userId, LocalDateTime actualizationDate, LocalDateTime creationDate, List<Item> items) {
        this.id = id;
        this.userId = userId;
        this.actualizationDate = actualizationDate;
        this.creationDate = creationDate;
        this.items = items;
    }

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getActualizationDate() {
        return actualizationDate;
    }

    public void setActualizationDate(LocalDateTime actualizationDate) {
        this.actualizationDate = actualizationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}