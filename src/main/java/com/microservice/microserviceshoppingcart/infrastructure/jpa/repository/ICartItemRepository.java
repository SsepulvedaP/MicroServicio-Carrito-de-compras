package com.microservice.microserviceshoppingcart.infrastructure.jpa.repository;

import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ItemCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ICartItemRepository extends JpaRepository<ItemCartEntity, Long> {

    List<ItemCartEntity> findAllByShoppingCartEntity_Id(Long shoppingCartId);
}
