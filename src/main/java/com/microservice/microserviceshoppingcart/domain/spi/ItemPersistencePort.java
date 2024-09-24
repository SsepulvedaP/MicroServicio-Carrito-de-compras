package com.microservice.microserviceshoppingcart.domain.spi;

import com.microservice.microserviceshoppingcart.domain.models.Item;

public interface ItemPersistencePort {
    Item save(Item item);
    Item findById(Long id);
    void deleteById(Long id);
}
