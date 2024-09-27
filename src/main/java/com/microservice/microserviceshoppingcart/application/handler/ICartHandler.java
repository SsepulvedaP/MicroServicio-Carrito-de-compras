package com.microservice.microserviceshoppingcart.application.handler;

import com.microservice.microserviceshoppingcart.application.dto.request.ItemRequest;
import com.microservice.microserviceshoppingcart.application.dto.response.ShoppingCartItemResponse;
import com.microservice.microserviceshoppingcart.domain.pagination.PageCustom;
import com.microservice.microserviceshoppingcart.domain.pagination.PageRequestCustom;

public interface ICartHandler {
    void addProductToCart(ItemRequest itemRequest, Long userId);
    void removeProduct(Long productId, Long userId);
    PageCustom<ShoppingCartItemResponse> getCartItems(Long userId, PageRequestCustom pageRequest, String categoryName, String brandName);

}
