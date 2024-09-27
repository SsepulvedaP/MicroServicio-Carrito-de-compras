package com.microservice.microserviceshoppingcart.domain.api;

import com.microservice.microserviceshoppingcart.domain.models.CartItems;
import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.pagination.PageCustom;
import com.microservice.microserviceshoppingcart.domain.pagination.PageRequestCustom;

public interface IShoppingCartServicePort {
    void addProduct(Item item, Long userId);
    void removeProduct(Long productId, Long userId);
    PageCustom<CartItems> getPaginatedCartItems(Long userId, PageRequestCustom pageRequest, String categoryName, String brandName);
}
