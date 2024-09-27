package com.microservice.microserviceshoppingcart.domain.exception;

public class InvalidPaginationLimit extends RuntimeException {
    public InvalidPaginationLimit(String message) {
        super(message);
    }
}
