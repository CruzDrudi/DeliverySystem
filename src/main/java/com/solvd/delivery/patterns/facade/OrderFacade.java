package com.solvd.delivery.patterns.facade;

import com.solvd.delivery.Main;
import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.interfaces.DiscountApplicator;
import com.solvd.delivery.model.interfaces.OrderValidator;
import com.solvd.delivery.exceptions.EmptyOrderException;

public class OrderFacade {
    public void processFullOrder(Order order,
                                 DiscountApplicator discount,
                                 OrderValidator validator,
                                 PaymentOption payment) {

        Main.LOGGER.info("=== FACADE: Starting process for Order #" + order.getId() + " ===");

        order.calculateTotal();

        if (discount != null) {
            order.getDiscountedTotal(discount);
        }

        if (order.validateOrder(validator)) {
            try {
                order.pay(payment);
                order.prepareOrder();
                order.assignRider();
            } catch (EmptyOrderException e) {
                Main.LOGGER.error("Facade Payment failed: " + e.getMessage());
            }
        } else {
            Main.LOGGER.warn("Facade aborted: Order failed validation.");
        }

        Main.LOGGER.info("=== FACADE: Process finished for Order #" + order.getId() + " ===");
    }
}