package com.raj.bookstore.orders.domain.models;

import com.raj.bookstore.orders.domain.models.OrderStatus;

public record OrderSummary(String orderNumber, OrderStatus status) {}
