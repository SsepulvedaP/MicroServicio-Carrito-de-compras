package com.microservice.microserviceshoppingcart.infrastructure.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message) {
        super(message);
    }
}
