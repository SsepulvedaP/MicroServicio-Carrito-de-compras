package com.microservice.microserviceshoppingcart.application.handler;


import com.microservice.microserviceshoppingcart.application.dto.request.ItemRequest;
import com.microservice.microserviceshoppingcart.application.mapper.ProductRequestMapper;
import com.microservice.microserviceshoppingcart.domain.api.IShoppingCartServicePort;
import com.microservice.microserviceshoppingcart.domain.models.Item;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CartHandler implements ICartHandler{

    private final IShoppingCartServicePort shoppingCarServicePort;
    private final ProductRequestMapper productRequestMapper;


    @Override
    public void addProductToCart(ItemRequest itemRequest, Long userId) {
        Item item = productRequestMapper.toItem(itemRequest);
        shoppingCarServicePort.addProduct(item, userId);
    }
}
