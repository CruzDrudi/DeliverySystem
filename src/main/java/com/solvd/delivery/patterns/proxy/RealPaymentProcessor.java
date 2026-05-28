package com.solvd.delivery.patterns.proxy;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.PaymentOption;

public class RealPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount, PaymentOption option) {
        Main.LOGGER.info("Processing actual bank transfer of $" + amount + " via " + option.getName() + "...");
    }
}