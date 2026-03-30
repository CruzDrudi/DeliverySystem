package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.exceptions.InvalidRatingException;
import com.solvd.delivery.model.exceptions.UnavailableChefException;
import com.solvd.delivery.model.exceptions.UnavailableRiderException;
import com.solvd.delivery.model.interfaces.Cancelable;
import com.solvd.delivery.model.interfaces.Payable;
import com.solvd.delivery.model.interfaces.Reviewable;
import com.solvd.delivery.model.interfaces.Trackable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Order implements Trackable, Reviewable, Payable, Cancelable {
    private static int idCounter = 1000;
    private int id;
    private List<OrderItem> orderItems;
    private Restaurant restaurant;
    private LocalDateTime creationTime;
    private Payment payment;
    private Review orderReview;
    private Rider assignedRider;
    private Chef assignedChef;
    private Client client;
    private Address address;
    private OrderStatus orderStatus;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public Order(List<OrderItem> orderItems, Restaurant restaurant,
                 LocalDateTime creationTime, Payment payment, Review orderReview,
                 Rider assignedRider,Chef assignedChef,  Client client, Address address, OrderStatus orderStatus) {
        this.id = idCounter++;
        this.orderItems = orderItems;
        this.restaurant = restaurant;
        this.creationTime = creationTime;
        this.payment = payment;
        this.orderReview = orderReview;
        this.assignedRider = assignedRider;
        this.assignedChef = assignedChef;
        this.client = client;
        this.address = address;
        this.orderStatus = orderStatus;
    }

    public Order(Restaurant restaurant, Client client) {
        this(new ArrayList<>(), restaurant, LocalDateTime.now(), null, null,
                null, null, client, client.getAddress(), OrderStatus.PENDING_PAYMENT);
        LOGGER.info("Order no. " + id + " created!");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Review getOrderReview() {
        return orderReview;
    }

    public void setOrderReview(Review orderReview) {
        this.orderReview = orderReview;
    }

    public Rider getAssignedRider() {
        return assignedRider;
    }

    public void setAssignedRider(Rider assignedRider) {
        this.assignedRider = assignedRider;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getNumberOfItems() {
        return orderItems.size();
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderStatus != OrderStatus.PENDING_PAYMENT) {
            LOGGER.warn("Order no. " + id + " is already paid and can't be modified.");
            return;
        }
        orderItems.add(orderItem);
        LOGGER.info(orderItem.getQuantity() + " " + orderItem.getProduct().getName() +
                " added to the order no. " + id + ".");
    }

    @Override
    public double getTotal() {
        if (orderItems.isEmpty()) {
            LOGGER.warn("Order no. " + id + " has no products.");
            return 0.0;
        }
        return orderItems.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public void assignRider() {
        if (orderStatus == OrderStatus.WAITING_FOR_RIDER) {
            for (Rider rider : restaurant.getRiders()) {
                if (!rider.isOccupied()) {
                    this.assignedRider = rider;
                    rider.setOccupied(true);
                    orderStatus = OrderStatus.ON_THE_WAY;
                    LOGGER.info("Rider " + rider.getName() + " assigned to order No. " + id + ".");
                    return;
                }
            }
        }
        LOGGER.error("It wasn't possible to assign a rider to order no. " + id + ".");
        throw new UnavailableRiderException("It wasn't possible to assign a chef to order no. " + id + ".");
    }

    public void assignChef() {
        if (orderStatus == OrderStatus.WAITING_FOR_CHEF) {
            for (Chef chef : restaurant.getChefs()) {
                if (!chef.isOccupied()) {
                    this.assignedChef = chef;
                    chef.setOccupied(true);
                    orderStatus = OrderStatus.PREPARING;
                    LOGGER.info("Chef " + chef.getName() + " assigned to order No. " + id + ".");
                    return;
                }
            }
        }
        LOGGER.error("It wasn't possible to assign a chef to order no. " + id + ".");
        throw new UnavailableChefException("It wasn't possible to assign a chef to order no. " + id + ".");
    }

    public void deliverOrder() {
        if (orderStatus == OrderStatus.ON_THE_WAY) {
            orderStatus = OrderStatus.DELIVERED;
            assignedRider.setOccupied(false);
            LOGGER.info("Order no. " + id + " successfully delivered to " + address.toString() + "!");
            return;
        }
        LOGGER.warn("It's not possible to deliver the order no. " + id + ".");
    }

    @Override
    public void pay(PaymentOption paymentOption) {
        if (orderStatus == OrderStatus.PENDING_PAYMENT && !orderItems.isEmpty()) {
            orderStatus = OrderStatus.WAITING_FOR_CHEF;
            double orderTotal = getTotal();
            this.payment = new Payment(orderTotal, paymentOption);
            LOGGER.info("Order no. " + id + " for $" + orderTotal + " successfully paid!");
            this.restaurant.addOrderToHistory(this);
            return;
        }
        LOGGER.warn("Order no. " + id + " can't be paid.");
    }

    @Override
    public void addReview(int rate, String comment) {
        if (orderStatus != OrderStatus.DELIVERED) {
            LOGGER.warn("Order no. " + id + "can't be reviewed.");
            return;
        }
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.orderReview = new Review(rate, comment);
        LOGGER.info("Order no. " + id + " reviewed with a rate of " + rate + " stars!");
    }

    @Override
    public void addReview(int rate) {
        if (orderStatus != OrderStatus.DELIVERED) {
            LOGGER.warn("Order no. " + id + "can't be reviewed.");
            return;
        }
        if (rate < 0 || rate > 5) {
            LOGGER.error("Invalid rating.");
            throw new InvalidRatingException("Invalid rating.");
        }
        this.orderReview = new Review(rate);
        LOGGER.info("Order no. " + id + " reviewed with a rate of " + rate + " stars!");
    }

    public void checkDelivery() {
        if (orderStatus == OrderStatus.ON_THE_WAY) {
            LOGGER.info("Order no. " + id + " is being delivered by rider " +
                    assignedRider.getName() + " to the address " + address.toString() + ".");
        } else {
            LOGGER.info("Order no. " + id + " is not being delivered.");
        }
    }

    public void prepareOrder() {
        if (orderStatus == OrderStatus.PREPARING) {
            orderStatus = OrderStatus.WAITING_FOR_RIDER;
            assignedChef.setOccupied(false);
            LOGGER.info("Order no. " + id + " has been prepared by chef " + assignedChef.getName() + "!");
        }
    }

    public int getEstimatedMinutes() {
        int estimatedMinutes;

        switch (orderStatus) {
            case OrderStatus.DELIVERED:
                estimatedMinutes = 0;
                break;

            case OrderStatus.ON_THE_WAY:
                estimatedMinutes = 15;
                break;

            default:
                int totalQuantity = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
                estimatedMinutes = 15 + totalQuantity * 4;
        }
        return estimatedMinutes;
    }

    @Override
    public void checkWaitTime() {
        int minutes = getEstimatedMinutes();

        if (minutes == 0) {
            LOGGER.info("The order no. " + id + " was already delivered.");
        } else {
            LOGGER.info("The estimated wait time for order no. " + id + " is " + minutes + " minutes!");
        }
    }

    @Override
    public void cancel() {
        if (orderStatus == OrderStatus.PENDING_PAYMENT || orderStatus == OrderStatus.WAITING_FOR_CHEF) {
            this.orderStatus = OrderStatus.CANCELED;
            LOGGER.info("Order no. " + id + " successfully cancelled.");
            return;
        }
        LOGGER.warn("Order no. " + id + " can't be cancelled!");
    }
}
