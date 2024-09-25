package com.microservice.microserviceshoppingcart.domain.usecase;

import com.microservice.microserviceshoppingcart.domain.api.IShoppingCartServicePort;
import com.microservice.microserviceshoppingcart.domain.exception.InvalidQuantityProducts;
import com.microservice.microserviceshoppingcart.domain.exception.OutOfStockException;
import com.microservice.microserviceshoppingcart.domain.exception.ShoppingCartNotFound;
import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.models.Product;
import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import com.microservice.microserviceshoppingcart.domain.spi.IProductPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ItemPersistencePort;
import com.microservice.microserviceshoppingcart.infrastructure.exception.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.microservice.microserviceshoppingcart.utils.Constants.*;

public class ShoppingCartUseCase implements IShoppingCartServicePort {

    private final IShoppingCartPersistencePort shoppingCartPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final ItemPersistencePort itemPersistencePort;

    public ShoppingCartUseCase(IShoppingCartPersistencePort shoppingCarPersistencePort, IProductPersistencePort productPersistencePort, ItemPersistencePort itemPersistencePort) {
        this.shoppingCartPersistencePort = shoppingCarPersistencePort;
        this.productPersistencePort = productPersistencePort;
        this.itemPersistencePort = itemPersistencePort;
    }

    @Override
    public void addProduct(Item item, Long userId) {
        ShoppingCart shoppingCart = getOrCreateShoppingCart(userId);
        item.setShoppingCart(shoppingCart);
        Product product = getProduct(item.getProductId());

        Optional<Item> existingItem = findExistingItem(shoppingCart, item.getProductId());

        existingItem.ifPresentOrElse(
                itemInCart -> {
                    int totalQuantityInCart = itemInCart.getQuantity() + item.getQuantity();
                    itemInCart.setShoppingCart(shoppingCart);
                    validateStock(product, totalQuantityInCart);
                    updateExistingItem(itemInCart, item.getQuantity());
                },
                () -> {
                    validateCategoryLimit(shoppingCart, product);
                    validateStock(product, item.getQuantity());
                    addNewItemToCart(shoppingCart, item);
                }
        );

        updateShoppingCart(shoppingCart);
    }

    @Override
    public void removeProduct(Long productId, Long userId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        boolean productRemoved = shoppingCart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!productRemoved) {
            throw new ItemNotFoundException(PRODUCT_NOT_FOUND_ON_CART);
        }

        shoppingCart.setActualizationDate(LocalDateTime.now());
        shoppingCartPersistencePort.updateCart(shoppingCart);
    }



    private ShoppingCart getOrCreateShoppingCart(Long userId) {
        return shoppingCartPersistencePort.getShoppingCartByUserId(userId)
                .orElseGet(() -> shoppingCartPersistencePort.createShoppingCart(userId));
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartPersistencePort.getShoppingCartByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFound(SHOPPING_CART_NOT_FOUND));
    }

    private Product getProduct(Long productId) {
        return productPersistencePort.getProductById(productId);
    }

    private void validateStock(Product product, int requestedQuantity) {
        if (product.getQuantity() < requestedQuantity) {
            LocalDateTime replenishmentDate = LocalDateTime.now().plusMonths(1);
            throw new OutOfStockException(OUT_OF_STOCK + replenishmentDate);
        }
    }

    private void validateCategoryLimit(ShoppingCart shoppingCart, Product product) {
        long categoryCount = countItemsInSameCategory(shoppingCart, product);
        if (categoryCount >= 3) {
            throw new InvalidQuantityProducts(INVALID_QUANTITY_PRODUCTS);
        }
    }

    private long countItemsInSameCategory(ShoppingCart shoppingCart, Product product) {
        // Contar cuántos productos en el carrito tienen categorías en común con el producto actual
        return shoppingCart.getItems().stream()
                .filter(i -> {
                    // Obtener el producto del ítem actual
                    Product itemProduct = productPersistencePort.getProductById(i.getProductId());
                    // Comparar las categorías del producto en el carrito con las del nuevo producto
                    return itemProduct.getCategories().stream()
                            .anyMatch(category -> product.getCategories().stream()
                                    .anyMatch(c -> c.getId().equals(category.getId())));
                })
                .count();
    }


    private Optional<Item> findExistingItem(ShoppingCart shoppingCart, Long productId) {
        return shoppingCart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();
    }

    private void updateExistingItem(Item existingItem, int additionalQuantity) {
        existingItem.setQuantity(existingItem.getQuantity() + additionalQuantity);
        itemPersistencePort.save(existingItem);
    }

    private void addNewItemToCart(ShoppingCart shoppingCart, Item newItem) {
        newItem.setShoppingCart(shoppingCart);
        Item savedItem = itemPersistencePort.save(newItem);
        shoppingCart.getItems().add(savedItem);
    }

    private void updateShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setActualizationDate(LocalDateTime.now());
        shoppingCartPersistencePort.updateCart(shoppingCart);
    }
}
