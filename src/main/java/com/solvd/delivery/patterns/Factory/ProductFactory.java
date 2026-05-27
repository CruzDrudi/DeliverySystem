package com.solvd.delivery.patterns.Factory;

import com.solvd.delivery.model.Beverage;
import com.solvd.delivery.model.Food;
import com.solvd.delivery.model.abstractClasses.Product;
import com.solvd.delivery.model.enums.ProductType;

public class ProductFactory {
    public static Product createProduct(ProductType type, String name, String description, double price, boolean specificFlag) {
        switch (type) {
            case FOOD -> {
                return new Food(name, description, price, specificFlag);
            }
            case BEVERAGE -> {
                return new Beverage(name, description, price, specificFlag);
            }
        }
        throw new IllegalArgumentException("Invalid product type");
    }
}
