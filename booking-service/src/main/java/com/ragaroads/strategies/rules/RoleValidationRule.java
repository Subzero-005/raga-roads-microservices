package com.ragaroads.strategies.rules;

import com.ragaroads.models.AllocationResult;
import java.util.Map;

public class RoleValidationRule {

    public AllocationResult validate(Map<String, Object> artist, Map<String, Object> concert) {
        String artistRole = (String) artist.get("artistRole");
        String requiredRole = (String) concert.get("artistRole");
        if (artistRole != null && artistRole.equalsIgnoreCase(requiredRole)) {
            return AllocationResult.confirmed("Role matches: " + artistRole);
        }
        return AllocationResult.rejected(
            "Role mismatch: artist is '" + artistRole + "', concert requires '" + requiredRole + "'"
        );
    }
}
