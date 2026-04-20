package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@EntityInfo("This represents a real menu of the restaurant")
public class Menu<T extends Product> {
    private String name;
    private List<T> items;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

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
        LOGGER.info(item.getName() + " added to the " + name + " menu.");
    }
}
