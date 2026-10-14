package com.ragaroads.services;

import com.ragaroads.dtos.BookingRequest;
import com.ragaroads.dtos.BookingResponse;
import com.ragaroads.exceptions.ArtistNotFoundException;
import com.ragaroads.exceptions.ConcertNotFoundException;
import com.ragaroads.models.AllocationResult;
import com.ragaroads.models.Booking;
import com.ragaroads.repositories.BookingRepository;
import com.ragaroads.strategies.AllocationStrategy;
import com.ragaroads.strategies.AllocationStrategyFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    private final WebClient artistWebClient;
    private final WebClient concertWebClient;
    private final BookingRepository bookingRepository;
    private final AllocationStrategyFactory strategyFactory;

    public BookingServiceImpl(@Qualifier("artistWebClient") WebClient artistWebClient,
                              @Qualifier("concertWebClient") WebClient concertWebClient,
                              BookingRepository bookingRepository,
                              AllocationStrategyFactory strategyFactory) {
        this.artistWebClient = artistWebClient;
        this.concertWebClient = concertWebClient;
        this.bookingRepository = bookingRepository;
        this.strategyFactory = strategyFactory;
    }

    private String currentToken() {
        ServletRequestAttributes attrs =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest().getHeader("Authorization") : null;
    }

    @Override
    public BookingResponse allocateArtist(BookingRequest request) {
        if (request.getArtistId() == null || request.getConcertId() == null) {
            throw new IllegalArgumentException("Both artistId and concertId are required");
        }
        String token = currentToken();

        Map<String, Object> artist;
        try {
            artist = artistWebClient.get()
                    .uri("/artists/{id}", request.getArtistId())
                    .headers(h -> { if (token != null) h.set("Authorization", token); })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ArtistNotFoundException(request.getArtistId());
        }

        Map<String, Object> concert;
        try {
            concert = concertWebClient.get()
                    .uri("/concerts/{id}", request.getConcertId())
                    .headers(h -> { if (token != null) h.set("Authorization", token); })
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ConcertNotFoundException(request.getConcertId());
        }

        String strategyType = (String) concert.get("strategyType");
        AllocationStrategy strategy = strategyFactory.getStrategy(strategyType);
        AllocationResult result = strategy.allocate(artist, concert);

        Booking booking = new Booking(
                null,
                request.getConcertId(),
                request.getArtistId(),
                result.getStatus(),
                strategy.getStrategyName(),
                LocalDate.now()
        );
        booking = bookingRepository.save(booking);

        return buildResponse(booking, artist, concert, result);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private BookingResponse buildResponse(Booking booking,
                                          Map<String, Object> artist,
                                          Map<String, Object> concert,
                                          AllocationResult result) {
        BookingResponse response = new BookingResponse();

        response.setBookingId(booking.getBookingId());
        response.setBookingStatus(booking.getBookingStatus());
        response.setStrategyApplied(booking.getStrategyApplied());
        response.setBookingDate(booking.getBookingDate().toString());

        if (!result.isConfirmed()) {
            response.setRejectionReason(result.getReason());
        }

        response.setConcertId(toLong(concert.get("concertId")));
        response.setConcertTitle((String) concert.get("concertTitle"));
        response.setConcertDate((String) concert.get("concertDate"));
        response.setConcertTime((String) concert.get("concertTime"));
        response.setConcertCity((String) concert.get("concertCity"));
        response.setConcertState((String) concert.get("concertState"));

        response.setArtistId(toLong(artist.get("artistId")));
        response.setArtistName((String) artist.get("artistName"));
        response.setArtistRole((String) artist.get("artistRole"));

        Double budget = toDouble(concert.get("budgetAllocated"));
        Double cost   = toDouble(artist.get("performanceCost"));
        response.setBudgetAllocated(budget);
        response.setArtistCost(cost);
        if (result.isConfirmed() && budget != null && cost != null) {
            response.setBudgetRemaining(budget - cost);
        }

        return response;
    }

    private Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    private Double toDouble(Object value) {
        return value == null ? null : ((Number) value).doubleValue();
    }
}
