package com.ragaroads.strategies;

import com.ragaroads.models.AllocationResult;
import java.util.Map;

public interface AllocationStrategy {
    AllocationResult allocate(Map<String, Object> artist, Map<String, Object> concert);
    String getStrategyName();
}
