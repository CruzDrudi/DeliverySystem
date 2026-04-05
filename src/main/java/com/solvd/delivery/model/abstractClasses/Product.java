package com.solvd.delivery.model.abstractClasses;

import com.solvd.delivery.model.interfaces.Taxable;

import java.util.Objects;

public abstract class Product implements Taxable {
    private String name;
    private String description;
    private double price;

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, double price) {
        this(name, null, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AbstractClasses.Product " + name + " priced $" + price + " USD with the description: " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.price == product.getPrice()
                && Objects.equals(this.name, product.getName())
                && Objects.equals(this.description, product.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}
