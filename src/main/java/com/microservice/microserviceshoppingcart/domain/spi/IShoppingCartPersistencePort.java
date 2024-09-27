package com.microservice.microserviceshoppingcart.domain.spi;


import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import java.util.Optional;

public interface IShoppingCartPersistencePort {
    ShoppingCart createShoppingCart(Long userId);
    Optional<ShoppingCart> getShoppingCartByUserId(Long userId);
    ShoppingCart getShoppingCartById(Long id);
    void updateCart(ShoppingCart shoppingCart);
    void removeProduct(Long productId, Long userId);
}
