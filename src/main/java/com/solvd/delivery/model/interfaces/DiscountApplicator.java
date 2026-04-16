package com.solvd.delivery.model.interfaces;

@FunctionalInterface
public interface DiscountApplicator {
    double applyDiscount(double originalNumber);
}
