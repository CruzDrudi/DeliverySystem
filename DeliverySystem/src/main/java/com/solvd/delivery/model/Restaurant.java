package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.Employee;
import com.solvd.delivery.model.exceptions.InvalidRatingException;
import com.solvd.delivery.model.interfaces.Reviewable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant implements Reviewable {
    private String name;
    private Address address;
    private List<Rider> riders;
    private List<Chef> chefs;
    private Review restaurantReview;
    private Map<Integer, Order> orderHistory;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    public Restaurant(String name, Address address, List<Rider> riders, List<Chef> chefs) {
        this.name = name;
        this.address = address;
        this.riders = riders;
        this.chefs = chefs;
        this.orderHistory = new HashMap<>();
        LOGGER.info("Restaurant " + name + " sited in " + address.toString() + " created!");
    }

    public Restaurant(String name, Address address) {
        this(name, address, new ArrayList<>(), new ArrayList<>());
    }

    public List<Rider> getRiders() {
        return riders;
    }

    public void setRiders(List<Rider> riders) {
        this.riders = riders;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(List<Chef> chefs) {
        this.chefs = chefs;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Review getRestaurantReview() {
        return restaurantReview;
    }

    public void setRestaurantReview(Review restaurantReview) {
        this.restaurantReview = restaurantReview;
    }

    public Map<Integer, Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(Map<Integer, Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRider(Rider rider) {
        if (rider != null) {
            riders.add(rider);
            LOGGER.info("Rider " + rider.getName() + " added to the restaurant " + name + ".");
        }
    }

    public void addChef(Chef chef) {
        if (chef != null) {
            chefs.add(chef);
            LOGGER.info("Chef " + chef.getName() + " added to the restaurant " + name + ".");
        }
    }

    public double calculateTotalPayroll() {
        double totalPayroll = 0.0;
        List<Employee> employees = new ArrayList<>();

        employees.addAll(riders);
        employees.addAll(chefs);

        for (Employee employee : employees) {
            totalPayroll += employee.getSalary(); // Polymorphism
        }


        LOGGER.info("The restaurant " + name + "'s monthly payroll is $" + totalPayroll + " USD");
        return totalPayroll;
    }

    @Override
    public void addReview(int rate, String comment) {
        this.restaurantReview = new Review(rate, comment);
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        LOGGER.info("Restaurant " + name + " reviewed with a rate of " + rate + " stars!");
    }

    @Override
    public void addReview(int rate) {
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.restaurantReview = new Review(rate);
        LOGGER.info("Restaurant " + name + " reviewed with a rate of " + rate + " stars!");
    }

    public void addOrderToHistory(Order order) {
        if (order != null) {
            orderHistory.put(order.getId(), order);
            LOGGER.info("Order no. " + order.getId() +
                    " has been added to the restaurant " + name + "'s order history!.");
        }
    }
}
