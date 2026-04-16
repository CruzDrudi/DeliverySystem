package com.solvd.delivery.model.interfaces;

import com.solvd.delivery.model.Order;

@FunctionalInterface
public interface ReceiptFormatter {
    String format(Order order);
}
