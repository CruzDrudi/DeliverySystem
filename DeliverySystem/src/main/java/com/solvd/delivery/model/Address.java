package com.solvd.delivery.model;

import java.util.Objects;

public class Address {
    private String street;
    private int number;
    private int floor;
    private String apartment;

    public Address(String street, int number, int floor, String apartment) {
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.apartment = apartment;
    }

    public Address(String street, int number) {
        this(street, number, 0, null);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return street + " no. " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return this.street.equals(address.getStreet())
                && this.number == address.getNumber()
                && this.floor == address.getFloor()
                && this.apartment.equals(address.getApartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, floor, apartment);
    }
}
