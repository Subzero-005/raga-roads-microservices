package com.ragaroads.strategies;

import com.ragaroads.models.AllocationResult;
import com.ragaroads.strategies.rules.AvailabilityValidationRule;
import com.ragaroads.strategies.rules.BudgetValidationRule;
import com.ragaroads.strategies.rules.LocationValidationRule;
import com.ragaroads.strategies.rules.RoleValidationRule;
import java.util.Map;

public class BalancedAllocationStrategy implements AllocationStrategy {

    private final RoleValidationRule roleRule = new RoleValidationRule();
    private final LocationValidationRule locationRule = new LocationValidationRule();
    private final AvailabilityValidationRule availabilityRule = new AvailabilityValidationRule();
    private final BudgetValidationRule budgetRule = new BudgetValidationRule();

    @Override
    public AllocationResult allocate(Map<String, Object> artist, Map<String, Object> concert) {
        AllocationResult result = roleRule.validate(artist, concert);
        if (!result.isConfirmed()) return result;

        result = locationRule.validate(artist, concert);
        if (!result.isConfirmed()) return result;

        result = availabilityRule.validate(artist, concert);
        if (!result.isConfirmed()) return result;

        result = budgetRule.validate(artist, concert);
        if (!result.isConfirmed()) return result;

        return AllocationResult.confirmed("Balanced strategy: all validations passed");
    }

    @Override
    public String getStrategyName() { return "BALANCED"; }
}
