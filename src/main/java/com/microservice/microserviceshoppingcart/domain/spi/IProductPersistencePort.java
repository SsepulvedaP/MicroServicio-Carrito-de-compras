package com.microservice.microserviceshoppingcart.domain.spi;

import com.microservice.microserviceshoppingcart.domain.models.Category;
import com.microservice.microserviceshoppingcart.domain.models.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface IProductPersistencePort {
    Product getProductById(Long id);
}
