package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BudgetValidationRuleTest {

    private BudgetValidationRule rule;

    @BeforeEach
    void setUp() {
        rule = new BudgetValidationRule();
    }

    private Map<String, Object> artist(double cost) {
        Map<String, Object> a = new HashMap<>();
        a.put("performanceCost", cost);
        return a;
    }

    private Map<String, Object> concert(double budget) {
        Map<String, Object> c = new HashMap<>();
        c.put("budgetAllocated", budget);
        return c;
    }

    @Test
    void validate_CostWithinBudget_ReturnsConfirmed() {
        AllocationResult result = rule.validate(artist(10000), concert(15000));
        assertTrue(result.isConfirmed());
        assertTrue(result.getReason().contains("within budget"));
    }

    @Test
    void validate_CostEqualsbudget_ReturnsConfirmed() {
        AllocationResult result = rule.validate(artist(13000), concert(13000));
        assertTrue(result.isConfirmed());
    }

    @Test
    void validate_CostExceedsBudget_ReturnsRejected() {
        AllocationResult result = rule.validate(artist(11000), concert(10000));
        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("exceeds concert budget"));
    }

    @Test
    void validate_ZeroBudget_ReturnsRejected() {
        AllocationResult result = rule.validate(artist(5000), concert(0));
        assertFalse(result.isConfirmed());
    }
}
