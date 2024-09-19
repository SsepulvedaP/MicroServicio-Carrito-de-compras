package com.microservice.microserviceshoppingcart.infrastructure.input.rest;


import com.microservice.microserviceshoppingcart.application.dto.request.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartRestController {

    private final ShoppingCart shoppingCart = new ShoppingCart();

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    public ResponseEntity<String> addProductToCart(@RequestBody ProductRequest productRequest) {
        shoppingCart.addProduct(productRequest);
        return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
    }

}

class ShoppingCart {
    private List<ProductRequest> products = new ArrayList<>();

    public void addProduct(ProductRequest productRequest) {
        products.add(productRequest);
    }

    public List<ProductRequest> getProducts() {
        return products;
    }
}
