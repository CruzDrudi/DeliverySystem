package com.solvd.delivery.model.interfaces;

import com.solvd.delivery.model.abstractClasses.PaymentOption;

public interface Payable {
    void pay(PaymentOption paymentOption);
    double calculateTotal();
}
