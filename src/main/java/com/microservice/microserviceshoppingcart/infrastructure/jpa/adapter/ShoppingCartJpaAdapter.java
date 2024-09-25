package com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter;



import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import com.microservice.microserviceshoppingcart.domain.spi.IShoppingCartPersistencePort;


import com.microservice.microserviceshoppingcart.infrastructure.exception.NotShoppingCartFound;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ShoppingCartEntity;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper.ShoppingCartEntityMapper;

import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.ICartItemRepository;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.IShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Optional;

import static com.microservice.microserviceshoppingcart.utils.Constants.NOT_SHOPPING_CART;

@Transactional
@RequiredArgsConstructor
public class ShoppingCartJpaAdapter implements IShoppingCartPersistencePort {

    private final IShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;




    @Override
    public ShoppingCart createShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setActualizationDate(LocalDateTime.now());

        ShoppingCartEntity shoppingCartEntity = shoppingCartEntityMapper.toEntity(shoppingCart);
        return shoppingCartEntityMapper.toShoppingCar(shoppingCartRepository.save(shoppingCartEntity));
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).map(shoppingCartEntityMapper::toShoppingCar);
    }


    @Override
    public ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .map(shoppingCartEntityMapper::toShoppingCar)
                .orElseThrow(() -> new NotShoppingCartFound(NOT_SHOPPING_CART));
    }

    @Override
    public void updateCart(ShoppingCart shoppingCart) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartEntityMapper.toEntityWithItems(shoppingCart);
        shoppingCartRepository.save(shoppingCartEntity);
    }

    @Override
    public void removeProduct(Long productId, Long userId) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId)
                .orElseThrow(() -> new NotShoppingCartFound(NOT_SHOPPING_CART));
        updateCart(shoppingCart);
    }

}