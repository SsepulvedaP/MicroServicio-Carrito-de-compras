package com.microservice.microserviceshoppingcart.domain.exception;

public class InvalidQuantityProducts extends RuntimeException {
    public InvalidQuantityProducts(String message) {
        super(message);
    }
}
