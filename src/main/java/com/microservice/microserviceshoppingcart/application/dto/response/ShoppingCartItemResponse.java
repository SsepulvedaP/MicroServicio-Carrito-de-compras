package com.microservice.microserviceshoppingcart.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShoppingCartItemResponse {
    private String productName;
    private String categoryName;
    private String brandName;
    private int quantityInCart;
    private double pricePerUnit;
    private int stockAvailable;
    private LocalDateTime replenishmentDate;
    private double totalPrice;
}

