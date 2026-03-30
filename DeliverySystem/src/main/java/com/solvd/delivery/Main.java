package com.solvd.delivery;

import com.solvd.delivery.model.*;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.abstractClasses.Product;
import com.solvd.delivery.model.abstractClasses.Vehicle;
import com.solvd.delivery.model.exceptions.EmptyOrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static {
        System.setProperty("log4j.configurationFile", "./DeliverySystem/src/main/resources/log4j2.xml");
    }

    public static final Logger LOGGER = LogManager.getLogger(Main.class);


    public static void main(String[] args) {
        Address addressRes = new Address("Viking's Road", 787);

        Restaurant ourRestaurant = new Restaurant("Fluffy Puppies", addressRes);

        Vehicle motorcycle = new Motorcycle("VXL 150", "Vespa", "AA551YJ", true);
        Vehicle car = new Car("Clio", "Renault", "HCI349", 3);

        Rider rider1 = new Rider("Peter Coldwick", "+1 987 548 888",
                motorcycle, 4.7);
        Rider rider2 = new Rider("Jessica Samson", "+54 9 351 584 6487",
                car, 5.2);

        Chef chef1 = new Chef("Carlos Heinz", "+33 6546 2548", 5.6, 1);
        Chef chef2 = new Chef("Karla Zielinski", "+33 7844 2590", 6.2, 2);

        ourRestaurant.addRider(rider1);
        ourRestaurant.addRider(rider2);

        ourRestaurant.addChef(chef1);
        ourRestaurant.addChef(chef2);

        LOGGER.info(chef1);
        LOGGER.info(chef2);

        double monthlyPayroll = ourRestaurant.calculateTotalPayroll();

        Address addressClient1 = new Address("Main Street", 1050, 4, "A");
        Address addressClient2 = new Address("Pope Francis Road", 33);

        Client client1 = new Client("Pedro", "+31 55 846 9855", addressClient1);
        Client client2 = new Client("Maria Jackson", 41575648,
                "+545 585 6412", addressClient2);

        Product product1 = new Food("French fries",
                "These are simple french fries.", 5.5, true);
        Product product2 = new Food("Mexican burger",
                "This is a spicy mexican burger.", 12.0, false);
        Product product3 = new Beverage("Beer",
                "This is cold pilsener beer.", 2.5, true);

        Order order1 = new Order(ourRestaurant, client1);

        OrderItem item1order1 = new OrderItem(product2);
        OrderItem item2order1 = new OrderItem(product1, 3);

        order1.getTotal();

        PaymentOption cash = new Card("Cash",
                "This option includes Visa and MasterCard", "Debit");
        PaymentOption debitCard = new Cash("Cash",
                "This option includes bills and coins.", "USD");

        try {
            order1.pay(cash);
        } catch (EmptyOrderException e) {
            LOGGER.error("Payment failed: " + e.getMessage());
        }

        order1.addOrderItem(item1order1);
        order1.addOrderItem(item2order1);

        try {
            order1.pay(cash);
        } catch (EmptyOrderException e) {
            LOGGER.error("Payment failed: " + e.getMessage());
        }

        order1.checkDelivery();

        Order order2 = new Order(ourRestaurant, client2);

        OrderItem item1order2 = new OrderItem(product2, 4);
        OrderItem item2order2 = new OrderItem(product1, 6);
        OrderItem item3order2 = new OrderItem(product3, 4);

        order2.addOrderItem(item1order2);
        order2.addOrderItem(item2order2);
        order2.addOrderItem(item3order2);

        order2.getTotal();

        try {
            order2.pay(debitCard);
        } catch (EmptyOrderException e) {
            LOGGER.error("Payment failed: " + e.getMessage());
        }

        order1.prepareOrder();

        order1.assignRider();

        order1.checkDelivery();
        order1.checkWaitTime();

        order2.checkWaitTime();

        order2.prepareOrder();

        order2.assignRider();

        order2.checkWaitTime();

        order1.deliverOrder();
        order1.checkWaitTime();

        rider1.addReview(5, "Everything was perfect.");

        order2.deliverOrder();

        order1.addReview(5);
        order2.addReview(3, "The food tasted good, but half the fries were cold!");
    }
}
