package com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter;

import com.microservice.microserviceshoppingcart.domain.spi.ITransactionsPersistencePort;
import com.microservice.microserviceshoppingcart.infrastructure.feign.client.TransactionsFeignClient;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransactionsFeignJpaAdapter implements ITransactionsPersistencePort {

    private final TransactionsFeignClient transactionsFeignClient;

    @Override
    public LocalDateTime nextSupplyDate(Long productId) {
        return transactionsFeignClient.nextSupplyDate(productId);
    }
}
