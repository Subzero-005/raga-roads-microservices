package com.ragaroads.strategies;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityAllocationStrategyTest {

    private AvailabilityAllocationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new AvailabilityAllocationStrategy();
    }

    private Map<String, Object> artist(String role, boolean available, double cost) {
        Map<String, Object> a = new HashMap<>();
        a.put("artistRole", role);
        a.put("available", available);
        a.put("performanceCost", cost);
        return a;
    }

    private Map<String, Object> concert(String role, String date, double budget) {
        Map<String, Object> c = new HashMap<>();
        c.put("artistRole", role);
        c.put("concertDate", date);
        c.put("budgetAllocated", budget);
        return c;
    }

    @Test
    void allocate_AllRulesPass_ReturnsConfirmed() {
        AllocationResult result = strategy.allocate(
            artist("Keyboardist", true, 10000),
            concert("Keyboardist", "2025-08-01", 12000)
        );
        assertTrue(result.isConfirmed());
        assertEquals("AVAILABILITY", strategy.getStrategyName());
    }

    @Test
    void allocate_SkipsLocation_DifferentCityStillConfirmed() {
        // AVAILABILITY does not check location — artist from different city should still pass
        Map<String, Object> a = artist("Keyboardist", true, 10000);
        a.put("city", "Delhi");
        a.put("state", "Delhi");

        Map<String, Object> c = concert("Keyboardist", "2025-08-01", 12000);
        c.put("concertCity", "Chennai");
        c.put("concertState", "Tamil Nadu");

        AllocationResult result = strategy.allocate(a, c);
        assertTrue(result.isConfirmed());
    }

    @Test
    void allocate_RoleMismatch_ReturnsRejected() {
        AllocationResult result = strategy.allocate(
            artist("Drummer", true, 8000),
            concert("Keyboardist", "2025-08-01", 12000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("Role mismatch"));
    }

    @Test
    void allocate_ArtistUnavailable_ReturnsRejected() {
        AllocationResult result = strategy.allocate(
            artist("Keyboardist", false, 10000),
            concert("Keyboardist", "2025-08-01", 12000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("not available"));
    }

    @Test
    void allocate_BudgetExceeded_ReturnsRejected() {
        AllocationResult result = strategy.allocate(
            artist("Keyboardist", true, 11000),
            concert("Keyboardist", "2025-08-01", 10000)
        );
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("exceeds concert budget"));
    }
}
