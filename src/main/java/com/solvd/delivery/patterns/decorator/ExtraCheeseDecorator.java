package com.solvd.delivery.patterns.decorator;

import com.solvd.delivery.model.abstractClasses.Product;

public class ExtraCheeseDecorator extends ProductDecorator {
    public ExtraCheeseDecorator(Product wrappedProduct) {
        super(wrappedProduct);
    }

    @Override
    public String getName() {
        // We append our extra text to the original name!
        return wrappedProduct.getName() + " (w/ Extra Cheese)";
    }

    @Override
    public double getPrice() {
        // We add $1.50 to whatever the original product cost!
        return wrappedProduct.getPrice() + 1.50;
    }

    @Override
    public double calculateTax() {
        return wrappedProduct.calculateTax();
    }
}
