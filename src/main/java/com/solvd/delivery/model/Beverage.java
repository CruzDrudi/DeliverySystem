package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.Product;

public class Beverage extends Product {
    private boolean containsAlcohol;

    public Beverage(String name, String description, double price, boolean containsAlcohol) {
        super(name, description, price);
        this.containsAlcohol = containsAlcohol;
    }

    public boolean isContainsAlcohol() {
        return containsAlcohol;
    }

    public void setContainsAlcohol(boolean containsAlcohol) {
        this.containsAlcohol = containsAlcohol;
    }

    @Override
    public double calculateTax() {
        if (containsAlcohol) {
            return getPrice() * 0.21;
        } else {
            return  getPrice() * 0.1;
        }
    }
}
