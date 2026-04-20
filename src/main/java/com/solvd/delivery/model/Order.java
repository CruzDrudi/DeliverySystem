package com.solvd.delivery.model;

import com.solvd.delivery.Main;
import com.solvd.delivery.annotations.EntityInfo;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.exceptions.EmptyOrderException;
import com.solvd.delivery.exceptions.InvalidRatingException;
import com.solvd.delivery.exceptions.UnavailableRiderException;
import com.solvd.delivery.model.enums.OrderStatus;
import com.solvd.delivery.model.interfaces.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EntityInfo("This represents a real order made to the restaurant")
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
    private double totalPrice;
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public Order(List<OrderItem> orderItems, Restaurant restaurant,
                 LocalDateTime creationTime, Payment payment, Review orderReview,
                 Rider assignedRider, Chef assignedChef, Client client, Address address, OrderStatus orderStatus) {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Chef getAssignedChef() {
        return assignedChef;
    }

    public void setAssignedChef(Chef assignedChef) {
        this.assignedChef = assignedChef;
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
    public double calculateTotal() {
        if (orderItems.isEmpty()) {
            LOGGER.warn("Order no. " + id + " has no products.");
            this.totalPrice = 0.0;
            return totalPrice;
        }
        this.totalPrice = orderItems.stream().mapToDouble(OrderItem::getSubtotal).sum();
        return totalPrice;
    }

    public void assignRider() {
        if (orderStatus == OrderStatus.WAITING_FOR_RIDER) {
            Rider availableRider = restaurant.getRiders().stream()
                    .filter(rider -> !rider.isOccupied())
                    .findFirst()
                    .orElseThrow(() -> {
                        LOGGER.error("It wasn't possible to assign a rider to order no. " + id + ".");
                        return new UnavailableRiderException("It wasn't possible to assign a rider to order no. " + id + ".");
                    });
            this.assignedRider = availableRider;
            availableRider.setOccupied(true);
            orderStatus = OrderStatus.ON_THE_WAY;
            LOGGER.info("Rider " + availableRider.getName() + " assigned to order no. " + id + ".");
        } else {
            LOGGER.warn("Can´t assign a rider. Order no. " + id + " is not ready.");
        }
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
    public void pay(PaymentOption paymentOption) throws EmptyOrderException {
        if (orderItems.isEmpty()) {
            LOGGER.error("Attempted to pay for an empty order.");
            throw new EmptyOrderException("Order no. " + id + " has no items and cannot be paid!");
        }
        if (orderStatus == OrderStatus.PENDING_PAYMENT) {
            orderStatus = OrderStatus.WAITING_FOR_CHEF;
            double orderTotal = this.totalPrice;
            this.payment = new Payment(orderTotal, paymentOption);
            LOGGER.info("Order no. " + id + " for $" + orderTotal + " successfully paid!");
            this.restaurant.setOrderReadyToPrepare(this);
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

    public double getDiscountedTotal(DiscountApplicator discountRule) {
        double originalTotal = this.calculateTotal();
        double discountedTotal = discountRule.applyDiscount(originalTotal);
        this.setTotalPrice(discountedTotal);
        LOGGER.info("Discount applied! The new total for order no. " + id + " is $" + discountedTotal + ".");
        return discountedTotal;
    }

    public boolean validateOrder(OrderValidator validator) {
        boolean isValid = validator.isValid(this);
        if (isValid) {
            LOGGER.info("Order no. " + id + " passed validation!");
        } else {
            LOGGER.warn("Order no. " + id + " failed validation.");
        }
        return isValid;
    }

    public void printReceipt(ReceiptFormatter formatter) {
        String receiptText = formatter.format(this);

        System.out.println("\n=== RESTAURANT RECEIPT ===");
        System.out.println(receiptText);
        System.out.println("==========================\n");
    }
}
