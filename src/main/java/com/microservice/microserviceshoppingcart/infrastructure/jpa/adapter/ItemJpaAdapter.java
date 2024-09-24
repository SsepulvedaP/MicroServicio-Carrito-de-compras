package com.microservice.microserviceshoppingcart.infrastructure.jpa.adapter;

import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.spi.ItemPersistencePort;
import com.microservice.microserviceshoppingcart.infrastructure.exception.NotShoppingCartFound;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ItemCartEntity;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.entity.ShoppingCartEntity;
import com.microservice.microserviceshoppingcart.infrastructure.exception.ItemNotFoundException;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.mapper.ItemCartEntityMapper;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.ICartItemRepository;
import com.microservice.microserviceshoppingcart.infrastructure.jpa.repository.IShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.microservice.microserviceshoppingcart.utils.Constants.NOT_ITEM_SHOPPING_CART;
import static com.microservice.microserviceshoppingcart.utils.Constants.NOT_SHOPPING_CART;

@Transactional
@RequiredArgsConstructor
public class ItemJpaAdapter implements ItemPersistencePort {

    private final ICartItemRepository itemRepository;
    private final ItemCartEntityMapper itemCartEntityMapper;
    private final IShoppingCartRepository shoppingCartRepository;

    @Override
    public Item save(Item item) {
        ItemCartEntity itemCartEntity = itemCartEntityMapper.toItemCartEntity(item);
        // Obtener el ShoppingCartEntity correspondiente
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(item.getShoppingCart().getId())
                .orElseThrow(() -> new NotShoppingCartFound(NOT_SHOPPING_CART));

        itemCartEntity.setShoppingCartEntity(shoppingCartEntity);
        shoppingCartEntity.getItems().add(itemCartEntity);

        itemRepository.save(itemCartEntity);
        return itemCartEntityMapper.toItem(itemCartEntity);
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .map(itemCartEntityMapper::toItem)
                .orElseThrow(() -> new ItemNotFoundException(NOT_ITEM_SHOPPING_CART));
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
