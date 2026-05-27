package com.solvd.delivery.patterns.Strategy;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.enums.OrderStatus;

public class DeliveredTimeStrategy implements EstimatedTimeStrategy {
    @Override
    public int calculateWaitTime(Order order) {
        return 0;
    }

    @Override
    public boolean supports(OrderStatus status) {
        return status ==  OrderStatus.DELIVERED;
    }
}