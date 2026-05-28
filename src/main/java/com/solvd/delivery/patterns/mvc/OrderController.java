package com.solvd.delivery.patterns.mvc;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.interfaces.DiscountApplicator;
import com.solvd.delivery.model.interfaces.OrderValidator;
import com.solvd.delivery.model.interfaces.ReceiptFormatter;
import com.solvd.delivery.patterns.facade.OrderFacade;
import com.solvd.delivery.utils.ReceiptPrinter;

public class OrderController {
    private OrderFacade orderFacade;

    public OrderController() {
        this.orderFacade = new OrderFacade();
    }

    public void handleOrderRequest(Order order, DiscountApplicator discount,
                                   OrderValidator validator, PaymentOption payment, ReceiptFormatter formatter) {

        orderFacade.processFullOrder(order, discount, validator, payment);
        ReceiptPrinter.printReceipt(order, formatter);
    }
}