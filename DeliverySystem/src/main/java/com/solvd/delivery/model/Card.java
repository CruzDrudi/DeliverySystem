package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.PaymentOption;

public class Card extends PaymentOption {
    private String type; // "Debit", "Credit"

    public Card(String name, String description, String type) {
        super(name, description);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
