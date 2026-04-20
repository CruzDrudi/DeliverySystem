package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.PaymentOption;

import java.time.LocalDateTime;

@EntityInfo
public record Payment(double total, LocalDateTime dateTime, PaymentOption paymentOption) {
    public Payment(double total, PaymentOption paymentOption) {
        this(total, LocalDateTime.now(), paymentOption);
    }
}
