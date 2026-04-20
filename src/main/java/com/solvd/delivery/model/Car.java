package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.Vehicle;
import com.solvd.delivery.model.enums.VehicleType;

@EntityInfo("This represents a real car")
public class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String name, String brand, String licensePlate, int numberOfDoors) {
        super(name, brand, licensePlate, VehicleType.CAR);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString() {
        return "Car named " + getName() + " brand " + getBrand() + " with "
                + getNumberOfDoors() + " doors and license plate " + getLicensePlate();
    }
}
