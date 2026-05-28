package com.solvd.delivery.patterns.decorator;

import com.solvd.delivery.model.abstractClasses.Product;

public abstract class ProductDecorator extends Product {
    protected Product wrappedProduct;

    public ProductDecorator(Product wrappedProduct) {
        super(wrappedProduct.getName(), wrappedProduct.getDescription(), wrappedProduct.getPrice());
        this.wrappedProduct = wrappedProduct;
    }

    @Override
    public String getName() {
        return wrappedProduct.getName();
    }

    @Override
    public String getDescription() {
        return wrappedProduct.getDescription();
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice();
    }

    @Override
    public double calculateTax() {
        return wrappedProduct.calculateTax();
    }
}
