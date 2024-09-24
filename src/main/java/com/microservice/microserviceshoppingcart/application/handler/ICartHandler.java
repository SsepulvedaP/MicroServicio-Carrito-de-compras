package com.microservice.microserviceshoppingcart.application.handler;

import com.microservice.microserviceshoppingcart.application.dto.request.ItemRequest;

public interface ICartHandler {
    void addProductToCart(ItemRequest itemRequest, Long userId);
}
