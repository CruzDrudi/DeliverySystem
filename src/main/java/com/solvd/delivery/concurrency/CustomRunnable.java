package com.solvd.delivery.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomRunnable implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(CustomRunnable.class);

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            LOGGER.info("Runnable " + Thread.currentThread().getName() + " running.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOGGER.error("Thread was interrupted: " + e.getMessage());
            }
        }
    }
}