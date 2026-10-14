package com.ragaroads.strategies;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BalancedAllocationStrategyTest {

    private BalancedAllocationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new BalancedAllocationStrategy();
    }

    private Map<String, Object> artist(String role, String city, String state,
                                       boolean available, double cost) {
        Map<String, Object> a = new HashMap<>();
        a.put("artistRole", role);
        a.put("city", city);
        a.put("state", state);
        a.put("available", available);
        a.put("performanceCost", cost);
        return a;
    }

    private Map<String, Object> concert(String role, String city, String state,
                                        String date, double budget) {
        Map<String, Object> c = new HashMap<>();
        c.put("artistRole", role);
        c.put("concertCity", city);
        c.put("concertState", state);
        c.put("concertDate", date);
        c.put("budgetAllocated", budget);
        return c;
    }

    @Test
    void allocate_AllRulesPass_ReturnsConfirmed() {
        AllocationResult result = strategy.allocate(
            artist("Guitarist", "Bangalore", "Karnataka", true, 9000),
            concert("Guitarist", "Bangalore", "Karnataka", "2025-08-01", 10000)
        );
        assertTrue(result.isConfirmed());
        assertEquals("BALANCED", strategy.getStrategyName());
    }

    @Test
    void allocate_RoleMismatch_ReturnsRejectedOnRole() {
        AllocationResult result = strategy.allocate(
            artist("Drummer", "Bangalore", "Karnataka", true, 9000),
            concert("Guitarist", "Bangalore", "Karnataka", "2025-08-01", 10000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("Role mismatch"));
    }

    @Test
    void allocate_LocationMismatch_ReturnsRejectedOnLocation() {
        AllocationResult result = strategy.allocate(
            artist("Guitarist", "Delhi", "Delhi", true, 9000),
            concert("Guitarist", "Bangalore", "Karnataka", "2025-08-01", 10000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("Location mismatch"));
    }

    @Test
    void allocate_ArtistUnavailable_ReturnsRejectedOnAvailability() {
        AllocationResult result = strategy.allocate(
            artist("Guitarist", "Bangalore", "Karnataka", false, 9000),
            concert("Guitarist", "Bangalore", "Karnataka", "2025-08-01", 10000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("not available"));
    }

    @Test
    void allocate_BudgetExceeded_ReturnsRejectedOnBudget() {
        AllocationResult result = strategy.allocate(
            artist("Guitarist", "Bangalore", "Karnataka", true, 15000),
            concert("Guitarist", "Bangalore", "Karnataka", "2025-08-01", 10000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("exceeds concert budget"));
    }
}
