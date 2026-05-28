package com.solvd.delivery.patterns.decorator;

import com.solvd.delivery.model.abstractClasses.Product;

public class BaconDecorator extends ProductDecorator {
    public BaconDecorator(Product wrappedProduct) {
        super(wrappedProduct);
    }

    @Override
    public String getName() {
        return wrappedProduct.getName() + " (w/ Bacon)";
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice() + 2.00;
    }

    @Override
    public double calculateTax() {
        return wrappedProduct.calculateTax();
    }
}
