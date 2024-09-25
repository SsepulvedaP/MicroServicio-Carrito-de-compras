package com.microservice.microserviceshoppingcart.domain.exception;

public class ShoppingCartNotFound extends RuntimeException {
    public ShoppingCartNotFound(String message) {
        super(message);
    }
}
