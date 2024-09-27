package com.microservice.microserviceshoppingcart.domain.usecase;

import com.microservice.microserviceshoppingcart.domain.api.IShoppingCartServicePort;
import com.microservice.microserviceshoppingcart.domain.exception.InvalidPaginationLimit;
import com.microservice.microserviceshoppingcart.domain.exception.InvalidQuantityProducts;
import com.microservice.microserviceshoppingcart.domain.exception.OutOfStockException;
import com.microservice.microserviceshoppingcart.domain.exception.ShoppingCartNotFound;
import com.microservice.microserviceshoppingcart.domain.models.CartItems;
import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.models.Product;
import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import com.microservice.microserviceshoppingcart.domain.pagination.PageCustom;
import com.microservice.microserviceshoppingcart.domain.pagination.PageRequestCustom;
import com.microservice.microserviceshoppingcart.domain.spi.IProductPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ITransactionsPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ItemPersistencePort;
import com.microservice.microserviceshoppingcart.infrastructure.exception.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


import static com.microservice.microserviceshoppingcart.utils.Constants.*;

public class ShoppingCartUseCase implements IShoppingCartServicePort {

    private final IShoppingCartPersistencePort shoppingCartPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final ItemPersistencePort itemPersistencePort;
    private final ITransactionsPersistencePort transactionsPersistencePort;

    public ShoppingCartUseCase(IShoppingCartPersistencePort shoppingCarPersistencePort, IProductPersistencePort productPersistencePort, ItemPersistencePort itemPersistencePort, ITransactionsPersistencePort transactionsPersistencePort) {
        this.shoppingCartPersistencePort = shoppingCarPersistencePort;
        this.productPersistencePort = productPersistencePort;
        this.itemPersistencePort = itemPersistencePort;
        this.transactionsPersistencePort = transactionsPersistencePort;
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

    @Override
    public PageCustom<CartItems> getPaginatedCartItems(Long userId, PageRequestCustom pageRequest, String categoryName, String brandName) {
        ShoppingCart shoppingCart = shoppingCartPersistencePort.getShoppingCartByUserId(userId)
                .orElseThrow(() -> new ShoppingCartNotFound(SHOPPING_CART_NOT_FOUND));

        //Funcion para mappear los items a CartItems
        List<CartItems> cartItemsList = shoppingCart.getItems().stream()
                .map(this::mapToCartItems)
                .filter(cartItem -> filterByCategoryAndBrand(cartItem, categoryName, brandName))
                .sorted(getComparator(pageRequest.isAscending()))
                .toList();

        if (cartItemsList.isEmpty()) {
            throw new OutOfStockException(NOT_FOUND_PRODUCT_IN_CART);
        }

        List<CartItems> paginatedItems = getPaginatedItems(cartItemsList, pageRequest);


        double totalPrice = paginatedItems.stream()
                .mapToDouble(CartItems::getTotalPrice)
                .sum();


        int totalElements = cartItemsList.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());

        return new PageCustom<>(
                paginatedItems,
                totalElements,
                totalPages,
                pageRequest.getPage(),
                pageRequest.isAscending(),
                totalPrice
        );
    }


    private CartItems mapToCartItems(Item item) {
        Product product = productPersistencePort.getProductById(item.getProductId());

        int stockAvailable = product.getQuantity();
        LocalDateTime replenishmentDate = transactionsPersistencePort.nextSupplyDate(item.getProductId());

        String categoryName = product.getCategories().isEmpty() ? null : product.getCategories().get(FIRST_CATEGORY).getName();
        String brandName = product.getBrand() != null ? product.getBrand().getName() : null;

        double totalPrice = product.getPrice() * item.getQuantity();

        return new CartItems(
                item.getProductId(),
                product.getName(),
                item.getQuantity(),
                product.getPrice(),
                totalPrice,
                stockAvailable,
                replenishmentDate,
                categoryName,
                brandName
        );
    }


    private boolean filterByCategoryAndBrand(CartItems cartItem, String categoryName, String brandName) {
        Product product = productPersistencePort.getProductById(cartItem.getProductId());

        boolean categoryMatches = (categoryName == null || categoryName.isEmpty()) ||
                product.getCategories().stream().anyMatch(category -> category.getName().equalsIgnoreCase(categoryName));

        boolean brandMatches = (brandName == null || brandName.isEmpty()) ||
                product.getBrand().getName().equalsIgnoreCase(brandName);

        return categoryMatches && brandMatches;
    }

    private Comparator<CartItems> getComparator(boolean ascending) {
        Comparator<CartItems> comparator = Comparator.comparing(CartItems::getProductName);
        return ascending ? comparator : comparator.reversed();
    }

    private List<CartItems> getPaginatedItems(List<CartItems> cartItemsList, PageRequestCustom pageRequest) {
        int start = pageRequest.getPage() * pageRequest.getSize();
        int end = Math.min(start + pageRequest.getSize(), cartItemsList.size());
        if (start >= cartItemsList.size()) {
            throw new InvalidPaginationLimit(INVALID_PAGINATION_LIMIT);
        }
        return cartItemsList.subList(start, end);
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
        int stockAvailable = product.getQuantity();
        if (stockAvailable < requestedQuantity) {
            LocalDateTime nextSupplyDate = transactionsPersistencePort.nextSupplyDate(product.getId());
            throw new OutOfStockException(OUT_OF_STOCK + nextSupplyDate);
        }
    }


    private void validateCategoryLimit(ShoppingCart shoppingCart, Product product) {
        long categoryCount = countItemsInSameCategory(shoppingCart, product);
        if (categoryCount >= 3) {
            throw new InvalidQuantityProducts(INVALID_QUANTITY_PRODUCTS);
        }
    }

    private long countItemsInSameCategory(ShoppingCart shoppingCart, Product product) {
        return shoppingCart.getItems().stream()
                .filter(i -> {
                    Product itemProduct = productPersistencePort.getProductById(i.getProductId());
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
