package com.microservice.microserviceshoppingcart.application.handler;


import com.microservice.microserviceshoppingcart.application.dto.request.ItemRequest;
import com.microservice.microserviceshoppingcart.application.dto.response.ShoppingCartItemResponse;
import com.microservice.microserviceshoppingcart.application.mapper.ProductRequestMapper;
import com.microservice.microserviceshoppingcart.application.mapper.ShoppingCartItemResponseMapper;
import com.microservice.microserviceshoppingcart.domain.api.IShoppingCartServicePort;
import com.microservice.microserviceshoppingcart.domain.models.CartItems;
import com.microservice.microserviceshoppingcart.domain.models.Item;


import com.microservice.microserviceshoppingcart.domain.pagination.PageCustom;
import com.microservice.microserviceshoppingcart.domain.pagination.PageRequestCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CartHandler implements ICartHandler{

    private final IShoppingCartServicePort shoppingCarServicePort;
    private final ProductRequestMapper productRequestMapper;
    private final IShoppingCartServicePort shoppingCartServicePort;
    private final ShoppingCartItemResponseMapper shoppingCartItemResponseMapper;


    @Override
    public void addProductToCart(ItemRequest itemRequest, Long userId) {
        Item item = productRequestMapper.toItem(itemRequest);
        shoppingCarServicePort.addProduct(item, userId);
    }

    @Override
    public void removeProduct(Long productId, Long userId) {
        shoppingCarServicePort.removeProduct(productId, userId);
    }

    @Override
    public PageCustom<ShoppingCartItemResponse> getCartItems(Long userId, PageRequestCustom pageRequest, String categoryName, String brandName) {
        PageCustom<CartItems> cartItemsPage = shoppingCartServicePort.getPaginatedCartItems(userId, pageRequest, categoryName, brandName);
        return new PageCustom<>(
                shoppingCartItemResponseMapper.toResponseList(cartItemsPage.getContent()),
                cartItemsPage.getTotalElements(),
                cartItemsPage.getTotalPages(),
                cartItemsPage.getCurrentPage(),
                cartItemsPage.isAscending(),
                cartItemsPage.getTotalPrice()
        );
    }

}
