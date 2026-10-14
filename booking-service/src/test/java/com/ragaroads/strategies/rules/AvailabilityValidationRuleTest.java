package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityValidationRuleTest {

    private AvailabilityValidationRule rule;

    @BeforeEach
    void setUp() {
        rule = new AvailabilityValidationRule();
    }

    @Test
    void validate_ArtistAvailable_ReturnsConfirmed() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("available", true);

        Map<String, Object> concert = new HashMap<>();
        concert.put("concertDate", "2025-08-15");

        AllocationResult result = rule.validate(artist, concert);

        assertTrue(result.isConfirmed());
        assertEquals("Artist is available", result.getReason());
    }

    @Test
    void validate_ArtistNotAvailable_ReturnsRejected() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("available", false);

        Map<String, Object> concert = new HashMap<>();
        concert.put("concertDate", "2025-08-15");

        AllocationResult result = rule.validate(artist, concert);

        assertFalse(result.isConfirmed());
        assertTrue(result.getReason().contains("not available"));
    }

    @Test
    void validate_NullAvailability_ReturnsRejected() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("available", null);

        Map<String, Object> concert = new HashMap<>();
        concert.put("concertDate", "2025-08-15");

        AllocationResult result = rule.validate(artist, concert);

        assertFalse(result.isConfirmed());
    }
}
