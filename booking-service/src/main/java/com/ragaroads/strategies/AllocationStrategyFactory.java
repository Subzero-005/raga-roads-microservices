package com.ragaroads.strategies;

import org.springframework.stereotype.Component;

@Component
public class AllocationStrategyFactory {

    public AllocationStrategy getStrategy(String strategyType) {
        if (strategyType == null) {
            throw new IllegalArgumentException("Strategy type must not be null");
        }
        switch (strategyType.toUpperCase()) {
            case "BALANCED":     return new BalancedAllocationStrategy();
            case "AVAILABILITY": return new AvailabilityAllocationStrategy();
            default: throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
        }
    }
}
