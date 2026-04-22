package com.solvd.delivery.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomThread extends Thread {
    public static final Logger LOGGER = LogManager.getLogger(CustomThread.class);

    public CustomThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            LOGGER.info("Thread " + getName() + " running.");
        }
    }
}