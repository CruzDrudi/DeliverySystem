package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.annotations.SensitiveData;
import com.solvd.delivery.model.abstractClasses.Person;
import com.solvd.delivery.patterns.observer.OrderStatusListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EntityInfo("This represents a real client of the restaurant")
public class Client extends Person implements OrderStatusListener {
    @SensitiveData
    private int nationalId;
    private Address address;

    public static final Logger LOGGER = LogManager.getLogger(Client.class);


    public Client(String name, int nationalId, String phoneNumber, Address address) {
        super(name, phoneNumber);
        this.nationalId = nationalId;
        this.address = address;
    }

    public Client(String name, String phoneNumber, Address address){
        this(name, 0, phoneNumber, address);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "Client " + getName() + ", national ID: " + getNationalId() +
                ", phone number: " + getPhoneNumber() + " and address " + getAddress().toString();
    }

    @Override
    public void onStatusChange(Order order) {
        LOGGER.info("[Notification for " + this.getName() + "]: "
                + order.getOrderStatus().getMessage());
    }
}