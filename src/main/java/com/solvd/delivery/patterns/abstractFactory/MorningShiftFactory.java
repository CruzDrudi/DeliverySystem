package com.solvd.delivery.patterns.abstractFactory;

import com.solvd.delivery.model.Chef;
import com.solvd.delivery.model.Rider;
import com.solvd.delivery.model.abstractClasses.Vehicle;

public class MorningShiftFactory implements EmployeeFactory {
    private static final double CHEF_BASE_RATE = 5.6;
    private static final double RIDER_BASE_RATE = 4.7;

    @Override
    public Chef createChef(String name, String phoneNumber, int kitchenNumber) {
        return new Chef(name, phoneNumber, CHEF_BASE_RATE, kitchenNumber);
    }

    @Override
    public Rider createRider(String name, String phoneNumber, Vehicle vehicle) {
        return new Rider(name, phoneNumber, vehicle, RIDER_BASE_RATE);
    }
}