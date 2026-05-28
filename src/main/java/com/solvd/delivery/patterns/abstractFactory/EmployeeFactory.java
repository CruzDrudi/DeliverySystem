package com.solvd.delivery.patterns.abstractFactory;

import com.solvd.delivery.model.Chef;
import com.solvd.delivery.model.Rider;
import com.solvd.delivery.model.abstractClasses.Vehicle;

public interface EmployeeFactory {
    Chef createChef(String name, String phoneNumber, int kitchenNumber);
    Rider createRider(String name, String phoneNumber, Vehicle vehicle);
}
