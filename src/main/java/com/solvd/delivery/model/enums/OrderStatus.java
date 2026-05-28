package com.solvd.delivery.model.enums;

public enum OrderStatus {
    PENDING_PAYMENT("Your order is pending payment."),
    WAITING_FOR_CHEF("Payment received! Waiting for a chef to start cooking."),
    PREPARING("The chef is preparing your food!"),
    WAITING_FOR_RIDER("Food is ready! Waiting for a rider."),
    ON_THE_WAY("BEEP BEEP! Your order is on the way!"),
    DELIVERED("DING! Your food has arrived. Enjoy!"),
    CANCELED("Your order has been canceled.");

    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}