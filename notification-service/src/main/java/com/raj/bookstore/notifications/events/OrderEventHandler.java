package com.raj.bookstore.notifications.events;

import com.raj.bookstore.notifications.domain.NotificationService;
import com.raj.bookstore.notifications.domain.OrderEventEntity;
import com.raj.bookstore.notifications.domain.OrderEventRepository;
import com.raj.bookstore.notifications.domain.models.OrderCancelledEvent;
import com.raj.bookstore.notifications.domain.models.OrderCreatedEvent;
import com.raj.bookstore.notifications.domain.models.OrderDeliveredEvent;
import com.raj.bookstore.notifications.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);
    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void handleNewOrder(OrderCreatedEvent event) {
        log.info("Order Created Event : {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received dublicate OrderCreatedEvent with eventId : {}", event.eventId());
            return;
        }
        notificationService.sendOrderCreatedNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void handleDeliveredOrder(OrderDeliveredEvent event) {
        log.info("Order Delivered Event : {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received dublicate OrderDeliveredEvent with eventId : {}", event.eventId());
            return;
        }
        notificationService.sendOrderDeliveredNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }

    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    void handleCancelledOrder(OrderCancelledEvent event) {
        log.info("Order CancelledEvent Event : {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received dublicate OrderCancelledEvent with eventId : {}", event.eventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }

    @RabbitListener(queues = "${notification.error-orders-queue}")
    void handleErrorOrder(OrderErrorEvent event) {
        log.info("Order ErrorEvent Event : {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received dublicate OrderErrorEvent with eventId : {}", event.eventId());
            return;
        }
        notificationService.sendOrderErrorEventNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }
}
