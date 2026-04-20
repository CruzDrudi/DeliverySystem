package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.Product;

@EntityInfo("This represents the food sold at the restaurant")
public class Food extends Product {
    private boolean isVegetarian;

    public Food(String name, String description, double price, boolean isVegetarian) {
        super(name, description, price);
        this.isVegetarian = isVegetarian;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    @Override
    public double calculateTax() {
        return getPrice() * 0.1;
    }
}
