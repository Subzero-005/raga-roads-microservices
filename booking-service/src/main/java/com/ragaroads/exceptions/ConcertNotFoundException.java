package com.ragaroads.exceptions;

public class ConcertNotFoundException extends RuntimeException {
    public ConcertNotFoundException(Long concertId) {
        super("Concert not found with id: " + concertId);
    }
}
