package com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper;

import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartEntityMapper {

    ShoppingCart toShoppingCar(ShoppingCartEntity shoppingCartEntity);
    @Mapping(target = "userId", source = "userId")
    ShoppingCartEntity toEntity(ShoppingCart shoppingCart);

}
