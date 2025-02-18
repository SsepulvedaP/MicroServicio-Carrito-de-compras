package com.microservice.microserviceshoppingcart.infrastructure.input.rest;

import com.microservice.microserviceshoppingcart.application.dto.request.ItemRequest;
import com.microservice.microserviceshoppingcart.application.handler.ICartHandler;
import com.microservice.microserviceshoppingcart.infrastructure.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCarRestController {

    private final ICartHandler cartHandler;

    @Operation(summary = "Añadir un producto al carrito de compras",
            description = "Añade un producto al carrito de compras de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto añadido al carrito"),
            @ApiResponse(responseCode = "400", description = "Petición inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/add-products")
    public ResponseEntity<Void> addProductToShoppingCart(@Valid @RequestBody ItemRequest itemRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        Long userId = userDetails.getId();
        cartHandler.addProductToCart(itemRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}