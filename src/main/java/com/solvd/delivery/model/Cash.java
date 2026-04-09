package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.enums.Currency;

public class Cash extends PaymentOption {
    private Currency currency;

    public Cash(String name, String description, Currency currency) {
        super(name, description);
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
