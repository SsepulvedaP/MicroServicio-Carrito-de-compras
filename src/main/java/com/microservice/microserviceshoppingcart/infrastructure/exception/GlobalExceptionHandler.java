package com.microservice.microserviceshoppingcart.infrastructure.exception;

import com.microservice.microserviceshoppingcart.domain.exception.InvalidQuantityProducts;
import com.microservice.microserviceshoppingcart.domain.exception.OutOfStockException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import static com.microservice.microserviceshoppingcart.utils.Constants.PRODUCT_NOT_FOUND;
import static com.microservice.microserviceshoppingcart.utils.Constants.UNEXPECTED_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Object> handleOutOfStockException(OutOfStockException ex) {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityProducts.class)
    public ResponseEntity<Object> handleInvalidQuantityProducts(InvalidQuantityProducts ex) {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException ex) {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Object> handleNotAuthorizedException(NotAuthorizedException ex) {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Object> handleFeignProductNotFoundException() {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FeignException.InternalServerError.class)
    public ResponseEntity<Object> handleFeignInternalServerError() {
        return new ResponseEntity<>(ExceptionResponseBuilder.buildResponse(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
