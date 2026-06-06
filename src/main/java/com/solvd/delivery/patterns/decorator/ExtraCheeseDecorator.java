package com.solvd.delivery.patterns.decorator;

import com.solvd.delivery.model.abstractClasses.Product;

public class ExtraCheeseDecorator extends ProductDecorator {
    public ExtraCheeseDecorator(Product wrappedProduct) {
        super(wrappedProduct);
    }

    @Override
    public String getName() {
        return wrappedProduct.getName() + " (w/ Extra Cheese)";
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice() + 1.50;
    }

    @Override
    public double calculateTax() {
        return wrappedProduct.calculateTax();
    }
}
