package com.solvd.delivery.patterns.proxy;

import com.solvd.delivery.model.abstractClasses.PaymentOption;

public interface PaymentProcessor {
    void processPayment(double amount, PaymentOption option);
}