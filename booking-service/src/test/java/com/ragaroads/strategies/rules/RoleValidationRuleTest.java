package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoleValidationRuleTest {

    private RoleValidationRule rule;

    @BeforeEach
    void setUp() {
        rule = new RoleValidationRule();
    }

    @Test
    void validate_RoleMatches_ReturnsConfirmed() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("artistRole", "Singer-Male");

        Map<String, Object> concert = new HashMap<>();
        concert.put("artistRole", "Singer-Male");

        AllocationResult result = rule.validate(artist, concert);

        assertTrue(result.isConfirmed());
        assertEquals("Confirmed", result.getStatus());
    }

    @Test
    void validate_RoleMismatch_ReturnsRejected() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("artistRole", "Guitarist");

        Map<String, Object> concert = new HashMap<>();
        concert.put("artistRole", "Drummer");

        AllocationResult result = rule.validate(artist, concert);

        assertFalse(result.isConfirmed());
        assertEquals("Rejected", result.getStatus());
        assertTrue(result.getReason().contains("Role mismatch"));
    }

    @Test
    void validate_RoleCaseInsensitive_ReturnsConfirmed() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("artistRole", "guitarist");

        Map<String, Object> concert = new HashMap<>();
        concert.put("artistRole", "Guitarist");

        AllocationResult result = rule.validate(artist, concert);

        assertTrue(result.isConfirmed());
    }

    @Test
    void validate_NullArtistRole_ReturnsRejected() {
        Map<String, Object> artist = new HashMap<>();
        artist.put("artistRole", null);

        Map<String, Object> concert = new HashMap<>();
        concert.put("artistRole", "Singer-Male");

        AllocationResult result = rule.validate(artist, concert);

        assertFalse(result.isConfirmed());
    }
}
