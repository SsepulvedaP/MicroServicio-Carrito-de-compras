package com.microservice.microserviceshoppingcart.application.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemRequest {
    @NotNull
    private Long productId;
    @NotNull
    @Positive
    private int quantity;
}
