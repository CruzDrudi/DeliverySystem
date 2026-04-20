package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.Vehicle;
import com.solvd.delivery.model.enums.VehicleType;

@EntityInfo("This represents a real motorcycle")
public class Motorcycle extends Vehicle {
    private boolean hasDeliveryBox;

    public Motorcycle(String name, String brand, String licensePlate, boolean hasDeliveryBox) {
        super(name, brand, licensePlate, VehicleType.MOTORCYCLE);
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
