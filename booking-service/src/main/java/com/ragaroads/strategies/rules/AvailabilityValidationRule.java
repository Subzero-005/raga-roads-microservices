package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import java.util.Map;

public class AvailabilityValidationRule {

    public AllocationResult validate(Map<String, Object> artist, Map<String, Object> concert) {
        Boolean available = (Boolean) artist.get("available");
        if (Boolean.TRUE.equals(available)) {
            return AllocationResult.confirmed("Artist is available");
        }
        return AllocationResult.rejected(
            "Artist is not available for concert on " + concert.get("concertDate")
        );
    }
}
