package com.solvd.delivery.patterns.strategy;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.OrderItem;
import com.solvd.delivery.model.enums.OrderStatus;

public class DefaultTimeStrategy implements EstimatedTimeStrategy {
    @Override
    public int calculateWaitTime(Order order) {
        int totalQuantity = order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();
        return 15 + totalQuantity * 4;
    }

    @Override
    public boolean supports(OrderStatus status) {
        return false;
    }
}