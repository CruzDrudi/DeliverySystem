package com.solvd.delivery.patterns.strategy;

import com.solvd.delivery.model.enums.OrderStatus;

import java.util.List;

public class WaitTimeStrategyRegistry {
    private final List<EstimatedTimeStrategy> strategies = List.of(
            new DeliveredTimeStrategy(),
            new OnTheWayTimeStrategy()
            // Future strategies for calculating wait times
    );
    private final EstimatedTimeStrategy defaultStrategy = new DefaultTimeStrategy();

    public EstimatedTimeStrategy getStrategyFor(OrderStatus status) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(status))
                .findFirst()
                .orElse(defaultStrategy);
    }
}