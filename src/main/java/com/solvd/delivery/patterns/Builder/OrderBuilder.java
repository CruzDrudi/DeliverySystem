package com.solvd.delivery.patterns.Builder;

import com.solvd.delivery.model.*;
import com.solvd.delivery.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private List<OrderItem> orderItems = new ArrayList<>();
    private Restaurant restaurant;
    private LocalDateTime creationTime;
    private Payment payment;
    private Review orderReview;
    private Rider assignedRider;
    private Chef assignedChef;
    private Client client;
    private Address address;
    private OrderStatus orderStatus;
    private double totalPrice;

    public OrderBuilder setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public OrderBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public OrderBuilder addOrderItem(OrderItem item) {
        orderItems.add(item);
        return this;
    }

    public OrderBuilder setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
        return this;
    }

    public Order build() {
        return new Order(orderItems,
                restaurant,
                LocalDateTime.now(),
                this.payment,
                this.orderReview,
                this.assignedRider,
                this.assignedChef,
                this.client,
                this.client != null ? this.client.getAddress() : null,
                this.orderStatus != null ? this.orderStatus : OrderStatus.PENDING_PAYMENT);
    }
}