package com.microservice.microserviceshoppingcart.application.mapper;

import com.microservice.microserviceshoppingcart.application.dto.response.ShoppingCartItemResponse;
import com.microservice.microserviceshoppingcart.domain.models.CartItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartItemResponseMapper {


    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "categoryName", target = "categoryName")
    @Mapping(source = "brandName", target = "brandName")
    @Mapping(source = "quantity", target = "quantityInCart")
    @Mapping(source = "pricePerUnit", target = "pricePerUnit")
    @Mapping(source = "stockAvailable", target = "stockAvailable")
    @Mapping(source = "replenishmentDate", target = "replenishmentDate")
    @Mapping(source = "totalPrice", target = "totalPrice")
    ShoppingCartItemResponse toResponse(CartItems cartItems);

    List<ShoppingCartItemResponse> toResponseList(List<CartItems> cartItemsList);
}
