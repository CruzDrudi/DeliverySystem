package com.solvd.delivery.model;

import com.solvd.delivery.model.abstractClasses.Vehicle;

public class Motorcycle extends Vehicle {
    private boolean hasDeliveryBox;

    public Motorcycle(String name, String brand, String licensePlate, boolean hasDeliveryBox) {
        super(name, brand, licensePlate);
        this.hasDeliveryBox = hasDeliveryBox;
    }

    public boolean isHasDeliveryBox() {
        return hasDeliveryBox;
    }

    public void setHasDeliveryBox(boolean hasDeliveryBox) {
        this.hasDeliveryBox = hasDeliveryBox;
    }

    @Override
    public String toString() {
        return "Motorcycle named " + getName() + " brand " + getBrand()
                + (isHasDeliveryBox()? "with" : "without")
                + " delivery box and license plate " + getLicensePlate() + ".";
    }
}
