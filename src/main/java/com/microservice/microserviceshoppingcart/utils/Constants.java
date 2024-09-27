package com.microservice.microserviceshoppingcart.utils;

public class Constants {

    public static final String OUT_OF_STOCK = "No hay suficiente stock del producto. Se abastece el: ";
    public static final String INVALID_QUANTITY_PRODUCTS = "Solo puedes agregar 3 productos por categoria";
    public static final String AUTH_TOKEN= "Authorization";
    public static final String AUTH_PREFIX= "Bearer ";
    public static final String ROLE_CLIENT = "ROLE_CLIENTE";
    public static final String NOT_SHOPPING_CART = "No se encontro el carrito de compras";
    public static final String NOT_ITEM_SHOPPING_CART = "No se encontro el item en el carrito de compras";
    public static final String AUTHORITY = "authority";
    public static final String NOT_FOUND_PRODUCT_IN_CART = "No se encontro el producto en el carrito de compras";
    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String STOCK_FEIGN_NAME = "stock-service";
    public static final String TRANSACTIONS_FEIGN_NAME = "transactions-service";
    public static final String URL_STOCK_FEIGN_CLIENT = "${feign.client.product-service.url}";
    public static final String URL_TRANSACTIONS_FEIGN_CLIENT = "${feign.client.transaction-service.url}";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String PRODUCT_NOT_FOUND = "No se encontraron productos";
    public static final String UNEXPECTED_ERROR = "Ha ocurrido un error inesperado";
    public static final String SHOPPING_CART_NOT_FOUND = "No se encontro el carrito de compras";
    public static final String PRODUCT_NOT_FOUND_ON_CART = "No se encontro el producto";
    public static final int FIRST_CATEGORY = 0;
    public static final String INVALID_PAGINATION_LIMIT = "El limite de paginacion no puede ser mayor a 100";
    public static final String SECURITY_PATH = "/shopping-cart/**";
}
