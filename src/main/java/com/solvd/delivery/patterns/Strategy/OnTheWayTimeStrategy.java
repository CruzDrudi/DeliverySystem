package com.solvd.delivery.patterns.Strategy;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.enums.OrderStatus;

public class OnTheWayTimeStrategy implements EstimatedTimeStrategy {
    @Override
    public int calculateWaitTime(Order order) {
        return 15;
    }

    @Override
    public boolean supports(OrderStatus status) {
        return status == OrderStatus.ON_THE_WAY;
    }
}