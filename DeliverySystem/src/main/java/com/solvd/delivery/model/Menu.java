package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.Product;

import java.util.ArrayList;
import java.util.List;

public class Menu<T extends Product> {
    private String name;
    private List<T> items;

    public Menu(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void addItem(T item) {
        items.add(item);
        System.out.println(item.getName() + " added to the " + name + " menu.");
    }
}
