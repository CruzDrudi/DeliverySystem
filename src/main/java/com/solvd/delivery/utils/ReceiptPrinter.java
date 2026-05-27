package com.solvd.delivery.utils;

import com.solvd.delivery.model.Order;
import com.solvd.delivery.model.interfaces.ReceiptFormatter;

public class ReceiptPrinter {
    public static void printReceipt(Order order, ReceiptFormatter formatter) {
        String receiptText = formatter.format(order);

        System.out.println("\n=== RESTAURANT RECEIPT ===");
        System.out.println(receiptText);
        System.out.println("==========================\n");
    }
}