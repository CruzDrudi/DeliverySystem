package com.solvd.delivery.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomRunnable implements Runnable {
    private final String name;
    private static final Logger LOGGER = LogManager.getLogger(CustomRunnable.class);

    public CustomRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            LOGGER.info("Runnable " + name + " running.");
        }
    }
}