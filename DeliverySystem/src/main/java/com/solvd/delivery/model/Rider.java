package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.Employee;
import com.solvd.delivery.model.abstractClasses.Vehicle;
import com.solvd.delivery.model.exceptions.InvalidRatingException;
import com.solvd.delivery.model.interfaces.Reviewable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rider extends Employee implements Reviewable {
    private Vehicle vehicle;
    private boolean occupied;
    private Review riderReview;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    public Rider(String name, String phoneNumber, Vehicle vehicle, boolean occupied, double salaryPerHour) {
        super(name, phoneNumber, salaryPerHour);
        this.vehicle = vehicle;
        this.occupied = occupied;
    }

    public Rider(String name, String phoneNumber, Vehicle vehicle, double salaryPerHour){
        this(name, phoneNumber, vehicle, false, salaryPerHour);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Rider " + getName() + " with the phone number " + getPhoneNumber() + " and with a salary of $"
                + getSalary() + " USD working with the vehicle " + getVehicle().toString() + ".";
    }

    @Override
    public double getSalary() {
        return getSalaryPerHour()
                * 9 // Riders work 9 hours a day, staying one more hour than chefs after the restaurant closes
                * 20; // 20 days a month
    }

    @Override
    public void addReview(int rate, String comment) {
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.riderReview = new Review(rate, comment);
        LOGGER.info("Rider " + getName() + " reviewed with a rate of " + rate + " stars!");
    }

    @Override
    public void addReview(int rate) {
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.riderReview = new Review(rate);
        LOGGER.info("Rider " + getName() + " reviewed with a rate of " + rate + " stars!");
    }
}
