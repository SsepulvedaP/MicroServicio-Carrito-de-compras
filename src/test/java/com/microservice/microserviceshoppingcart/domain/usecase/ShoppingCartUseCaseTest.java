package com.microservice.microserviceshoppingcart.domain.usecase;

import com.microservice.microserviceshoppingcart.domain.exception.InvalidQuantityProducts;
import com.microservice.microserviceshoppingcart.domain.exception.OutOfStockException;
import com.microservice.microserviceshoppingcart.domain.models.Category;
import com.microservice.microserviceshoppingcart.domain.models.Item;
import com.microservice.microserviceshoppingcart.domain.models.Product;
import com.microservice.microserviceshoppingcart.domain.models.ShoppingCart;
import com.microservice.microserviceshoppingcart.domain.spi.IProductPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.IShoppingCartPersistencePort;
import com.microservice.microserviceshoppingcart.domain.spi.ItemPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartUseCaseTest {

    @Mock
    private IShoppingCartPersistencePort shoppingCartPersistencePort;

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private ItemPersistencePort itemPersistencePort;

    @InjectMocks
    private ShoppingCartUseCase shoppingCartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddNewProductToCart() {
        // Arrange
        Long userId = 1L;
        Item item = new Item();
        item.setProductId(1L);
        item.setQuantity(1);

        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setCategories(new ArrayList<>());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(new ArrayList<>());

        when(shoppingCartPersistencePort.getShoppingCartByUserId(userId)).thenReturn(Optional.of(shoppingCart));
        when(productPersistencePort.getProductById(1L)).thenReturn(product);
        when(itemPersistencePort.save(any(Item.class))).thenReturn(item);

        // Act
        shoppingCartUseCase.addProduct(item, userId);

        // Assert
        verify(itemPersistencePort, times(1)).save(any(Item.class));
        verify(shoppingCartPersistencePort, times(1)).updateCart(any(ShoppingCart.class));
    }

    @Test
    void shouldUpdateExistingProductQuantityInCart() {
        // Arrange
        Long userId = 1L;
        Item existingItem = new Item();
        existingItem.setProductId(1L);
        existingItem.setQuantity(1);

        Item newItem = new Item();
        newItem.setProductId(1L);
        newItem.setQuantity(3);

        Product product = new Product();
        product.setId(1L);
        product.setQuantity(10);
        product.setCategories(Collections.emptyList());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(Collections.singletonList(existingItem));

        when(shoppingCartPersistencePort.getShoppingCartByUserId(userId)).thenReturn(Optional.of(shoppingCart));
        when(productPersistencePort.getProductById(1L)).thenReturn(product);
        when(itemPersistencePort.save(existingItem)).thenReturn(existingItem);

        // Act
        shoppingCartUseCase.addProduct(newItem, userId);

        // Assert
        assertEquals(4, existingItem.getQuantity());
        verify(itemPersistencePort, times(1)).save(existingItem);
        verify(shoppingCartPersistencePort, times(1)).updateCart(any(ShoppingCart.class));
    }

    @Test
    void shouldThrowOutOfStockExceptionWhenProductIsOutOfStock() {
        // Arrange
        Long userId = 1L;
        Item item = new Item();
        item.setProductId(1L);
        item.setQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setQuantity(5); // Stock insuficiente

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(Collections.emptyList());

        when(shoppingCartPersistencePort.getShoppingCartByUserId(userId)).thenReturn(Optional.of(shoppingCart));
        when(productPersistencePort.getProductById(1L)).thenReturn(product);

        // Act & Assert
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> shoppingCartUseCase.addProduct(item, userId));

        assertTrue(exception.getMessage().contains("No hay suficiente stock del producto"));
        verify(itemPersistencePort, never()).save(any(Item.class)); // No debería guardar el ítem
    }

    @Test
    void shouldThrowInvalidQuantityProductsWhenCategoryLimitExceeded() {
        // Arrange
        Long userId = 1L;

        // Crear nuevo ítem a agregar con la misma categoría "Electronics"
        Item newItem = new Item();
        newItem.setProductId(1L); // ID del producto
        newItem.setQuantity(1);

        // Producto que se intenta agregar con categoría "Electronics"
        Product product = new Product();
        product.setId(1L);
        product.setCategories(Collections.singletonList(new Category(1L, "Electronics", "description")));

        // Crear ítems existentes en el carrito
        Item existingItem1 = new Item();
        existingItem1.setProductId(2L);
        existingItem1.setQuantity(1);
        Product product1 = new Product();
        product1.setId(2L);
        product1.setCategories(Collections.singletonList(new Category(1L, "Electronics", "description")));

        Item existingItem2 = new Item();
        existingItem2.setProductId(3L);
        existingItem2.setQuantity(1);
        Product product2 = new Product();
        product2.setId(3L);
        product2.setCategories(Collections.singletonList(new Category(1L, "Electronics", "description")));

        Item existingItem3 = new Item();
        existingItem3.setProductId(4L);
        existingItem3.setQuantity(1);
        Product product3 = new Product();
        product3.setId(4L);
        product3.setCategories(Collections.singletonList(new Category(1L, "Electronics", "description")));

        // Crear carrito de compras con los ítems existentes
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(Arrays.asList(existingItem1, existingItem2, existingItem3));

        // Mock configurations
        when(shoppingCartPersistencePort.getShoppingCartByUserId(userId)).thenReturn(Optional.of(shoppingCart));
        when(productPersistencePort.getProductById(1L)).thenReturn(product);
        when(productPersistencePort.getProductById(2L)).thenReturn(product1);
        when(productPersistencePort.getProductById(3L)).thenReturn(product2);
        when(productPersistencePort.getProductById(4L)).thenReturn(product3);

        // Act & Assert
        InvalidQuantityProducts exception = assertThrows(InvalidQuantityProducts.class,
                () -> shoppingCartUseCase.addProduct(newItem, userId));

        // Verificar mensaje de excepción
        assertEquals("Solo puedes agregar 3 productos por categoria", exception.getMessage());

        // Verificar que los mocks fueron llamados
        verify(shoppingCartPersistencePort).getShoppingCartByUserId(userId);
        verify(productPersistencePort).getProductById(1L);
        verify(productPersistencePort).getProductById(2L);
        verify(productPersistencePort).getProductById(3L);
        verify(productPersistencePort).getProductById(4L);
    }


}
