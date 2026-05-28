package com.solvd.delivery;

import com.solvd.delivery.concurrency.Connection;
import com.solvd.delivery.concurrency.ConnectionPool;
import com.solvd.delivery.concurrency.CustomRunnable;
import com.solvd.delivery.concurrency.CustomThread;
import com.solvd.delivery.model.*;
import com.solvd.delivery.model.abstractClasses.PaymentOption;
import com.solvd.delivery.model.abstractClasses.Product;
import com.solvd.delivery.model.abstractClasses.Vehicle;
import com.solvd.delivery.exceptions.EmptyOrderException;
import com.solvd.delivery.model.enums.Currency;
import com.solvd.delivery.model.enums.ProductType;
import com.solvd.delivery.model.interfaces.DiscountApplicator;
import com.solvd.delivery.model.interfaces.OrderValidator;
import com.solvd.delivery.model.interfaces.ReceiptFormatter;
import com.solvd.delivery.patterns.abstractFactory.EmployeeFactory;
import com.solvd.delivery.patterns.abstractFactory.MorningShiftFactory;
import com.solvd.delivery.patterns.builder.OrderBuilder;
import com.solvd.delivery.patterns.decorator.BaconDecorator;
import com.solvd.delivery.patterns.decorator.ExtraCheeseDecorator;
import com.solvd.delivery.patterns.facade.OrderFacade;
import com.solvd.delivery.patterns.factory.ProductFactory;
import com.solvd.delivery.utils.ObjectPrinter;
import com.solvd.delivery.utils.PrintedObject;
import com.solvd.delivery.utils.ReceiptPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Address addressRes = new Address("Viking's Road", 787);

        Restaurant ourRestaurant = new Restaurant("Fluffy Puppies", addressRes);

        Vehicle motorcycle = new Motorcycle("VXL 150", "Vespa", "AA551YJ", true);
        Vehicle car = new Car("Clio", "Renault", "HCI349", 3);

        EmployeeFactory shiftFactory = new MorningShiftFactory();

        Rider rider1 = shiftFactory.createRider("Peter Coldwick", "+1 987 548 888", motorcycle);
        Rider rider2 = shiftFactory.createRider("Jessica Samson", "+54 9 351 584 6487", car);

        Chef chef1 = shiftFactory.createChef("Carlos Heinz", "+33 6546 2548", 1);
        Chef chef2 = shiftFactory.createChef("Karla Zielinski", "+33 7844 2590", 2);

        EmployeeRoster<Chef> kitchenStaff = new EmployeeRoster<>("Kitchen");
        kitchenStaff.clockInEmployee(chef1);

        Menu<Food> foodMenu = new Menu<>("Main Courses");
        foodMenu.addItem((Food) ProductFactory.createProduct(ProductType.FOOD,
                "Mexican burger", "Spicy", 12.0, false));

        Menu<Beverage> drinksMenu = new Menu<>("Drinks");
        drinksMenu.addItem((Beverage) ProductFactory.createProduct(ProductType.BEVERAGE,
                "Beer", "Cold Ale", 2.5, true));

        ourRestaurant.addRider(rider1);
        ourRestaurant.addRider(rider2);

        ourRestaurant.addChef(chef1);
        ourRestaurant.addChef(chef2);

        LOGGER.info(chef1);
        LOGGER.info(chef2);

        double monthlyPayroll = ourRestaurant.calculateTotalPayroll();

        Address addressClient1 = new Address("Main Street", 1050, 4, "A");
        Address addressClient2 = new Address("Pope Francis Road", 33);

        Client client1 = new Client("Pedro Zielinsky", "+31 55 846 9855", addressClient1);
        Client client2 = new Client("Maria Jackson", 41575648,
                "+545 585 6412", addressClient2);

        Product product1 = ProductFactory.createProduct(ProductType.FOOD, "French fries",
                "These are simple french fries.", 5.5, true);

        Product product2 = ProductFactory.createProduct(ProductType.FOOD,"Mexican burger",
                "This is a spicy mexican burger.", 12.0, false);
        product2 = new ExtraCheeseDecorator(product2);
        product2 = new BaconDecorator(product2);

        Product product3 = ProductFactory.createProduct(ProductType.BEVERAGE,"Beer",
                "This is cold pilsener beer.", 2.5, true);

        Order order1 = new OrderBuilder()
                .setRestaurant(ourRestaurant)
                .setClient(client1)
                .build();

        OrderItem item1order1 = new OrderItem(product2);
        OrderItem item2order1 = new OrderItem(product1, 3);

        order1.calculateTotal();

        Order order2 = new OrderBuilder()
                .setClient(client2)
                .setRestaurant(ourRestaurant)
                .addOrderItem(new OrderItem(product2, 4))
                .addOrderItem(new OrderItem(product1, 6))
                .addOrderItem(new OrderItem(product3, 4))
                .build();

        PaymentOption debitCard = new Card("Card", "This option includes Visa and MasterCard", "Debit");
        PaymentOption cash = new Cash("Cash", "This option includes bills and coins.", Currency.USD);
        DiscountApplicator vipDiscount = total -> total * 0.80;
        DiscountApplicator fiveDollarsOff = total -> total - 5.00;
        OrderValidator minimumPriceRule = order -> order.getTotalPrice() >= 15.00;
        OrderValidator notEmptyRule = order -> !order.getOrderItems().isEmpty();

        // --- 2. Add items to Order 1 ---
        order1.addOrderItem(item1order1);
        order1.addOrderItem(item2order1);


        OrderFacade orderFacade = new OrderFacade();
        orderFacade.processFullOrder(order1, vipDiscount, minimumPriceRule, cash);
        orderFacade.processFullOrder(order2, fiveDollarsOff, notEmptyRule, debitCard);

        ReceiptFormatter kitchenTicket = order ->
                "KITCHEN TICKET: Order #" + order.getId() + " \nItems to cook: " + order.getNumberOfItems();

        ReceiptFormatter customerReceipt = order ->
                "Thank you for eating at Fluffy Puppies!\nOrder #" + order.getId()
                        + " | Total Due: $" + order.getTotalPrice();

        ReceiptPrinter.printReceipt(order1, kitchenTicket);
        ReceiptPrinter.printReceipt(order2, customerReceipt);

        rider1.addReview(5, "Everything was perfect.");

        order2.deliverOrder();

        order1.addReview(5);
        order2.addReview(3, "The food tasted good, but half the fries were cold!");

        Evaluation<Chef> chefEval = new Evaluation<>(chef1, 5, "Cooks the burgers perfectly!");
        LOGGER.info(chefEval.toString());


    }

    private static Connection acquireOrThrow(ConnectionPool pool, int taskId) {
        try {
            LOGGER.info("Task-" + taskId + " [" + Thread.currentThread().getName() + "] → waiting for connection...");
            Connection c = pool.acquire();
            LOGGER.info("Task-" + taskId + " [" + Thread.currentThread().getName() + "] → got " + c);
            return c;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static void useAndRelease(ConnectionPool pool, Connection conn, int taskId) {
        try {
            Thread.sleep(2_000);
            pool.release(conn);
            LOGGER.info("Task-" + taskId + " [" + Thread.currentThread().getName() + "] → released " + conn);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
