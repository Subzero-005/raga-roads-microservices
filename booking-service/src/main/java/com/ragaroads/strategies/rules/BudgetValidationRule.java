package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import java.util.Map;

public class BudgetValidationRule {

    public AllocationResult validate(Map<String, Object> artist, Map<String, Object> concert) {
        double cost   = ((Number) artist.get("performanceCost")).doubleValue();
        double budget = ((Number) concert.get("budgetAllocated")).doubleValue();
        if (cost <= budget) {
            return AllocationResult.confirmed("Artist cost " + cost + " is within budget " + budget);
        }
        return AllocationResult.rejected(
            "Artist cost " + cost + " exceeds concert budget " + budget
        );
    }
}
