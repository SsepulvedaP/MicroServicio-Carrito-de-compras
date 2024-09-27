package com.microservice.microserviceshoppingcart.infrastructure.feign.client;

import com.microservice.microserviceshoppingcart.domain.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.microservice.microserviceshoppingcart.utils.Constants.STOCK_FEIGN_NAME;
import static com.microservice.microserviceshoppingcart.utils.Constants.URL_STOCK_FEIGN_CLIENT;

@FeignClient(name = STOCK_FEIGN_NAME, url = URL_STOCK_FEIGN_CLIENT)
public interface StockFeignClient {

        @GetMapping("/products/{id}")
        ResponseEntity<Product> getProductById(@PathVariable Long id);

}
