package com.solvd.delivery.model;

import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.Employee;

@EntityInfo("This represents a real chef working at the restaurant")
public class Chef extends Employee {
    private int kitchenNumber;

    public Chef(String name, String phoneNumber, double salaryPerHour, int kitchenNumber) {
        super(name, phoneNumber, salaryPerHour);
        this.kitchenNumber = kitchenNumber;
    }

    public int getKitchenNumber() {
        return kitchenNumber;
    }

    public void setKitchenNumber(int kitchenNumber) {
        this.kitchenNumber = kitchenNumber;
    }

    @Override
    public String toString() {
        return "Chef " + getName() + " with the phone number " + getPhoneNumber() + " and with a salary of $"
                + getSalary() + " USD working at the kitchen number " + getKitchenNumber() + ".";
    }

    @Override
    public double getSalary() {
        return getSalaryPerHour()
                * 8 // Chefs work 8 hours a day
                * 20; // 20 days per month
    }
}
