package com.raj.bookstore.orders.domain;

import com.raj.bookstore.orders.domain.models.CreateOrderRequest;
import com.raj.bookstore.orders.domain.models.CreateOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK");

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
   // private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository,OrderValidator orderValidator){
       this.orderRepository = orderRepository;
       this.orderValidator = orderValidator;
    }
    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        orderValidator.validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created Order with orderNumber={}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
