package com.ragaroads.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllocationStrategyFactoryTest {

    private AllocationStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new AllocationStrategyFactory();
    }

    @Test
    void getStrategy_BALANCED_ReturnsBalancedStrategy() {
        AllocationStrategy strategy = factory.getStrategy("BALANCED");
        assertNotNull(strategy);
        assertEquals("BALANCED", strategy.getStrategyName());
        assertInstanceOf(BalancedAllocationStrategy.class, strategy);
    }

    @Test
    void getStrategy_AVAILABILITY_ReturnsAvailabilityStrategy() {
        AllocationStrategy strategy = factory.getStrategy("AVAILABILITY");
        assertNotNull(strategy);
        assertEquals("AVAILABILITY", strategy.getStrategyName());
        assertInstanceOf(AvailabilityAllocationStrategy.class, strategy);
    }

    @Test
    void getStrategy_UnknownType_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> factory.getStrategy("UNKNOWN"));
    }

    @Test
    void getStrategy_NullType_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> factory.getStrategy(null));
    }
}
