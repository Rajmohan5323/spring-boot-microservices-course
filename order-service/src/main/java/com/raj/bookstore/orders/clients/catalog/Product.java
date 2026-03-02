package com.raj.bookstore.orders.clients.catalog;

import java.math.BigDecimal;
// Receive the product information

public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
