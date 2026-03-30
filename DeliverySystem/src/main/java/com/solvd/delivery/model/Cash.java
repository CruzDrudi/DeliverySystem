package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.PaymentOption;

public class Cash extends PaymentOption {
    private String currency;

    public Cash(String name, String description, String currency) {
        super(name, description);
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
