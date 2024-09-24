package com.microservice.microserviceshoppingcart.domain.api;

import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;

public interface IShoppingCartServicePort {
    void addProduct(Item item, Long userId);
}
