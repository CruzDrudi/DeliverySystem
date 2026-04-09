package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.Employee;
import com.solvd.delivery.exceptions.InvalidRatingException;
import com.solvd.delivery.model.enums.OrderStatus;
import com.solvd.delivery.model.interfaces.Reviewable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Restaurant implements Reviewable {
    private String name;
    private Address address;
    private List<Rider> riders;
    private List<Chef> chefs;
    private Deque<Review> restaurantReviews;    private Map<Integer, Order> orderHistory;
    private Queue<Order> pendingOrders;
    private Set historicClients;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    public Restaurant(String name, Address address, List<Rider> riders, List<Chef> chefs) {
        this.name = name;
        this.address = address;
        this.riders = riders;
        this.chefs = chefs;
        this.orderHistory = new HashMap<>();
        this.pendingOrders = new LinkedList<>();
        this.historicClients = new HashSet<>();
        this.restaurantReviews = new ArrayDeque<>();
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

    public Queue<Order> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Queue<Order> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Map<Integer, Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(Map<Integer, Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Deque<Review> getRestaurantReviews() {
        return restaurantReviews;
    }

    public void setRestaurantReviews(Deque<Review> restaurantReviews) {
        this.restaurantReviews = restaurantReviews;
    }

    public Set getHistoricClients() {
        return historicClients;
    }

    public void setHistoricClients(Set historicClients) {
        this.historicClients = historicClients;
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
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.restaurantReviews.addFirst(new Review(rate, comment));
        LOGGER.info("Restaurant " + name + " reviewed with a rate of " + rate + " stars!");
    }

    @Override
    public void addReview(int rate) {
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.restaurantReviews.addFirst(new Review(rate));
        LOGGER.info("Restaurant " + name + " reviewed with a rate of " + rate + " stars!");
    }

    public void addOrderToHistory(Order order) {
        if (order != null) {
            orderHistory.put(order.getId(), order);
            LOGGER.info("Order no. " + order.getId() +
                    " has been added to the restaurant " + name + "'s order history.");
        }
    }

    public void addPendingOrder(Order order) {
        if (order != null) {
            pendingOrders.offer(order);
            LOGGER.info("Order no." + order.getId() + " added to the restaurant " + name + "'s kitchen.");
        }
    }

    public void setOrderReadyToPrepare(Order order) {
        if (order != null) {
            addOrderToHistory(order);
            historicClients.add(order.getClient());
            addPendingOrder(order);
            assignPendingOrdersToChefs();
        }
    }

    public void assignPendingOrdersToChefs() {
        for (Chef chef : chefs) {
            if (!chef.isOccupied() && !pendingOrders.isEmpty()) {
                Order oldestOrder = pendingOrders.poll();
                oldestOrder.setAssignedChef(chef);
                chef.setOccupied(true);
                oldestOrder.setOrderStatus(OrderStatus.PREPARING);
                LOGGER.info("Chef " + chef.getName() + " is now preparing Order no. " + oldestOrder.getId() + ".");
            }
        }
    }
}
