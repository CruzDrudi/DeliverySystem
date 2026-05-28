package com.solvd.delivery.patterns.abstractFactory;

import com.solvd.delivery.model.Chef;
import com.solvd.delivery.model.Rider;
import com.solvd.delivery.model.abstractClasses.Vehicle;

public class NightShiftFactory implements EmployeeFactory {
    private static final double CHEF_PREMIUM_RATE = 8.4;
    private static final double RIDER_PREMIUM_RATE = 7.0;

    @Override
    public Chef createChef(String name, String phoneNumber, int kitchenNumber) {
        return new Chef(name, phoneNumber, CHEF_PREMIUM_RATE, kitchenNumber);
    }

    @Override
    public Rider createRider(String name, String phoneNumber, Vehicle vehicle) {
        return new Rider(name, phoneNumber, vehicle, RIDER_PREMIUM_RATE);
    }
}