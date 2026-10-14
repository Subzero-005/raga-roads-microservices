package com.ragaroads.services;

import java.util.Map;
import java.util.Optional;

public interface ConcertService {
    Optional<Map<String, Object>> getConcert(Long concertId);
}
