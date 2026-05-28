package com.solvd.delivery.patterns.proxy;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.abstractClasses.Product;

public class PaymentProxy implements PaymentProcessor {
    private PaymentProcessor paymentProcessor = new RealPaymentProcessor();

    @Override
    public void processPayment(double amount, PaymentOption option) {
        Main.LOGGER.info("SECURITY CHECK: Verifying payment details...");
        if (amount <= 0.00) {
            throw new IllegalArgumentException("Amount must be greater than $0");
        }
        paymentProcessor.processPayment(amount, option);
    }
}
