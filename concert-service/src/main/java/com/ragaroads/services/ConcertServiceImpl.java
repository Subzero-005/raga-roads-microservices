package com.ragaroads.services;

import com.ragaroads.models.Concert;
import com.ragaroads.repositories.ConcertRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertServiceImpl(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public Optional<Map<String, Object>> getConcert(Long concertId) {
        return concertRepository.findById(concertId).map(this::toMap);
    }

    private Map<String, Object> toMap(Concert c) {
        Map<String, Object> map = new HashMap<>();
        map.put("concertId",       c.getConcertId());
        map.put("concertTitle",    c.getConcertTitle());
        map.put("concertDate",     c.getConcertDate());
        map.put("concertTime",     c.getConcertTime());
        map.put("concertCity",     c.getConcertCity());
        map.put("concertState",    c.getConcertState());
        map.put("strategyType",    c.getStrategyType());
        map.put("artistRole",      c.getArtistRole());
        map.put("budgetAllocated", c.getBudgetAllocated());
        return map;
    }
}
