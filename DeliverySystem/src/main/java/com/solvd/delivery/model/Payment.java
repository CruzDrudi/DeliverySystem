package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.PaymentOption;

import java.time.LocalDateTime;

public class Payment {
    private double total;
    private LocalDateTime dateTime;
    private PaymentOption paymentOption;

    public Payment(double total, LocalDateTime dateTime, PaymentOption paymentOption) {
        this.total = total;
        this.dateTime = dateTime;
        this.paymentOption = paymentOption;
    }

    public Payment(double total, PaymentOption paymentOption) {
        this(total, LocalDateTime.now(), paymentOption);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }
}
