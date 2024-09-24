package com.microservice.microserviceshoppingcart.infrastructure.configuration;

import com.microservice.microserviceshoppingcart.domain.api.IShoppingCartServicePort;
import com.microservice.microserviceshoppingcart.domain.spi.IProductPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ISecurityPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ItemPersistencePort;
import com.microservice.microserviceshoppingcart.domain.usecase.ShoppingCartUseCase;
import com.microservice.microserviceshoppingcart.infrastructure.feign.client.StockFeignClient;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter.ShoppingCartJpaAdapter;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter.StockFeignJpaAdapter;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter.ItemJpaAdapter;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper.ItemCartEntityMapper;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper.ShoppingCartEntityMapper;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.ICartItemRepository;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.IShoppingCartRepository;
import com.microservice.microserviceshoppingcart.infrastructure.security.SecurityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IShoppingCartRepository shoppingCarRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;
    private final ItemCartEntityMapper itemCartEntityMapper;
    private final StockFeignClient stockFeignClient;
    private final ICartItemRepository cartItemRepository;

    @Bean
    public IShoppingCartPersistencePort shoppingCarPersistencePort() {
        return new ShoppingCartJpaAdapter(shoppingCarRepository, shoppingCartEntityMapper, cartItemRepository);
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new StockFeignJpaAdapter(stockFeignClient);
    }

    @Bean
    public ItemPersistencePort itemPersistencePort() {
        return new ItemJpaAdapter(cartItemRepository, itemCartEntityMapper, shoppingCarRepository);
    }

    @Bean
    public IShoppingCartServicePort shoppingCarServicePort(IShoppingCartPersistencePort shoppingCarPersistencePort, IProductPersistencePort productPersistencePort, ItemPersistencePort itemPersistencePort) {
        return new ShoppingCartUseCase(shoppingCarPersistencePort, productPersistencePort, itemPersistencePort);
    }

    @Bean
    public ISecurityPersistencePort securityPersistencePort() {
        return new SecurityAdapter();
    }
}