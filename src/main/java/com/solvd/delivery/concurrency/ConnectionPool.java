package com.solvd.delivery.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final int POOL_SIZE = 5;
    private final BlockingQueue<Connection> free = new LinkedBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection c = new Connection();
            c.open();
            free.offer(c);
        }
    }

    private static class Holder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    public Connection acquire() throws InterruptedException {
        return free.take();
    }

    public void release(Connection c) throws InterruptedException {
        free.put(c);
    }

    public void shutdown() {
        for (Connection c : free) c.close();
        free.clear();
    }
}
