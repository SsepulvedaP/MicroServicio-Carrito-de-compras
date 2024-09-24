package com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter;


import com.microservice.microserviceshoppingcart.domain.models.Product;
import com.microservice.microserviceshoppingcart.domain.spi.IProductPersistencePort;
import com.microservice.microserviceshoppingcart.infrastructure.feign.client.StockFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class StockFeignJpaAdapter implements IProductPersistencePort {

    private final StockFeignClient stockFeignClient;


    @Override
    public Product getProductById(Long productId) {
        ResponseEntity<Product> response = stockFeignClient.getProductById(productId);
        return response.getBody();
    }
}