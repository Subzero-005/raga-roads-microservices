package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocationValidationRuleTest {

    private LocationValidationRule rule;

    @BeforeEach
    void setUp() {
        rule = new LocationValidationRule();
    }

    private Map<String, Object> artist(String city, String state) {
        Map<String, Object> a = new HashMap<>();
        a.put("city", city);
        a.put("state", state);
        return a;
    }

    private Map<String, Object> concert(String city, String state) {
        Map<String, Object> c = new HashMap<>();
        c.put("concertCity", city);
        c.put("concertState", state);
        return c;
    }

    @Test
    void validate_SameCity_ReturnsConfirmed() {
        AllocationResult result = rule.validate(artist("Chennai", "Tamil Nadu"), concert("Chennai", "Telangana"));
        assertTrue(result.isConfirmed());
        assertTrue(result.getReason().contains("same city"));
    }

    @Test
    void validate_SameState_ReturnsConfirmed() {
        AllocationResult result = rule.validate(artist("Coimbatore", "Tamil Nadu"), concert("Chennai", "Tamil Nadu"));
        assertTrue(result.isConfirmed());
        assertTrue(result.getReason().contains("same state"));
    }

    @Test
    void validate_DifferentCityAndState_ReturnsRejected() {
        AllocationResult result = rule.validate(artist("Chennai", "Tamil Nadu"), concert("Bangalore", "Karnataka"));
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("Location mismatch"));
    }

    @Test
    void validate_CityMatchCaseInsensitive_ReturnsConfirmed() {
        AllocationResult result = rule.validate(artist("chennai", "Tamil Nadu"), concert("Chennai", "Tamil Nadu"));
        assertTrue(result.isConfirmed());
    }
}
