package com.microservice.microserviceshoppingcart.infrastructure.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
