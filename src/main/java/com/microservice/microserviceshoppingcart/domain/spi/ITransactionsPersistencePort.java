package com.microservice.microserviceshoppingcart.domain.spi;

import java.time.LocalDateTime;

public interface ITransactionsPersistencePort {
    LocalDateTime nextSupplyDate(Long productId);
}
