package com.solvd.delivery.patterns.observer;

import com.solvd.delivery.model.Order;

public interface OrderStatusListener {
    void onStatusChange(Order order);
}