package com.solvd.delivery.model.abstractClasses;

import com.solvd.delivery.model.enums.VehicleType;

public abstract class Vehicle {
    private String name;
    private String brand;
    private VehicleType type;
    private String licensePlate;

    public Vehicle(String name, String brand, String licensePlate, VehicleType type) {
        this.name = name;
        this.brand = brand;
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
