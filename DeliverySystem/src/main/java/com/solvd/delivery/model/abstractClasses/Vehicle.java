package com.solvd.delivery.model.abstractClasses;

public abstract class Vehicle {
    private String name;
    private String brand;
    private String licensePlate;

    public Vehicle(String name, String brand, String licensePlate) {
        this.name = name;
        this.brand = brand;
        this.licensePlate = licensePlate;
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
