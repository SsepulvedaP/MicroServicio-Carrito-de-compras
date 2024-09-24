package com.microservice.microserviceshoppingcart.infrastructure.jpa.repository;


import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    boolean existsByUserId(Long userId);
    Optional<ShoppingCartEntity> findByUserId(Long userId);
}
