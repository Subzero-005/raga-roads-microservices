package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import java.util.Map;

public class LocationValidationRule {

    public AllocationResult validate(Map<String, Object> artist, Map<String, Object> concert) {
        String artistCity  = (String) artist.get("city");
        String artistState = (String) artist.get("state");
        String concertCity  = (String) concert.get("concertCity");
        String concertState = (String) concert.get("concertState");

        if (artistCity != null && artistCity.equalsIgnoreCase(concertCity)) {
            return AllocationResult.confirmed("Artist is in the same city: " + artistCity);
        }
        if (artistState != null && artistState.equalsIgnoreCase(concertState)) {
            return AllocationResult.confirmed("Artist is in the same state: " + artistState);
        }
        return AllocationResult.rejected(
            "Location mismatch: artist in " + artistCity + ", " + artistState
            + " — concert in " + concertCity + ", " + concertState
        );
    }
}
