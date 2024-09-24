package com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper;

import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ItemCartEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemCartEntityMapper {

    @Mapping(target = "shoppingCart", ignore = true)
    Item toItem(ItemCartEntity itemCartEntity);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    ItemCartEntity toItemCartEntity(Item item);

}
