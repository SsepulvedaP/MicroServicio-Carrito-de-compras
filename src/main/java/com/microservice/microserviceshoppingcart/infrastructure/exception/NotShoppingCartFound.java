package com.microservice.microserviceshoppingcart.infrastructure.exception;

public class NotShoppingCartFound extends RuntimeException {
    public NotShoppingCartFound(String message) {
        super(message);
    }
}
