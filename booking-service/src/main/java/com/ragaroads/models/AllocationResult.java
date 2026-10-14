package com.ragaroads.models;

public class AllocationResult {

    private final boolean confirmed;
    private final String reason;

    private AllocationResult(boolean confirmed, String reason) {
        this.confirmed = confirmed;
        this.reason = reason;
    }

    public static AllocationResult confirmed(String reason) {
        return new AllocationResult(true, reason);
    }

    public static AllocationResult rejected(String reason) {
        return new AllocationResult(false, reason);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getReason() { return reason; }
    public String getStatus() { return confirmed ? "Confirmed" : "Rejected"; }
}
