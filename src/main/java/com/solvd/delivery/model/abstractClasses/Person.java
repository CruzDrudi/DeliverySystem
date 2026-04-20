package com.solvd.delivery.model.abstractClasses;

import com.solvd.delivery.annotations.SensitiveData;

public abstract class Person {
    private String name;
    private String phoneNumber;

    public Person(String name, String phoneNumber, int nationalId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}