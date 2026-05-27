package com.solvd.delivery.patterns.Strategy;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.enums.OrderStatus;

public interface EstimatedTimeStrategy {
    int calculateWaitTime(Order order);
    boolean supports(OrderStatus status);
}