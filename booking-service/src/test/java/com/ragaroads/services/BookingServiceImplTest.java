package com.ragaroads.services;

import com.ragaroads.dtos.BookingRequest;
import com.ragaroads.dtos.BookingResponse;
import com.ragaroads.models.Booking;
import com.ragaroads.repositories.BookingRepository;
import com.ragaroads.strategies.AllocationStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock private WebClient artistWebClient;
    @Mock private WebClient concertWebClient;
    @Mock private BookingRepository bookingRepository;

    private final AllocationStrategyFactory strategyFactory = new AllocationStrategyFactory();
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingServiceImpl(
            artistWebClient, concertWebClient, bookingRepository, strategyFactory
        );
    }

    private Map<String, Object> artistData(String role, String city, String state,
                                           boolean available, double cost) {
        Map<String, Object> a = new HashMap<>();
        a.put("artistId", 1);
        a.put("artistName", "Test Artist");
        a.put("artistRole", role);
        a.put("city", city);
        a.put("state", state);
        a.put("available", available);
        a.put("performanceCost", cost);
        return a;
    }

    private Map<String, Object> concertData(String role, String city, String state,
                                            String strategyType, double budget) {
        Map<String, Object> c = new HashMap<>();
        c.put("concertId", 1);
        c.put("concertTitle", "Test Concert");
        c.put("concertDate", "2025-08-01");
        c.put("concertTime", "18:00");
        c.put("concertCity", city);
        c.put("concertState", state);
        c.put("strategyType", strategyType);
        c.put("artistRole", role);
        c.put("budgetAllocated", budget);
        return c;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void stubWebClients(Map<String, Object> artist, Map<String, Object> concert) {
        WebClient.RequestHeadersUriSpec artistGet = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec artistUri   = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec artistResp        = mock(WebClient.ResponseSpec.class);
        when(artistWebClient.get()).thenReturn(artistGet);
        when(artistGet.uri(anyString(), any(Object.class))).thenReturn(artistUri);
        when(artistUri.headers(any())).thenReturn(artistUri);
        when(artistUri.retrieve()).thenReturn(artistResp);
        when(artistResp.bodyToMono(any(ParameterizedTypeReference.class)))
            .thenReturn(Mono.just(artist));

        WebClient.RequestHeadersUriSpec concertGet = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec concertUri    = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec concertResp         = mock(WebClient.ResponseSpec.class);
        when(concertWebClient.get()).thenReturn(concertGet);
        when(concertGet.uri(anyString(), any(Object.class))).thenReturn(concertUri);
        when(concertUri.headers(any())).thenReturn(concertUri);
        when(concertUri.retrieve()).thenReturn(concertResp);
        when(concertResp.bodyToMono(any(ParameterizedTypeReference.class)))
            .thenReturn(Mono.just(concert));
    }

    @Test
    void allocateArtist_BalancedStrategy_AllPass_ReturnsConfirmed() {
        Map<String, Object> artist  = artistData("Guitarist", "Bangalore", "Karnataka", true, 9000);
        Map<String, Object> concert = concertData("Guitarist", "Bangalore", "Karnataka", "BALANCED", 10000);

        stubWebClients(artist, concert);
        when(bookingRepository.save(any(Booking.class)))
            .thenReturn(new Booking(1L, 1L, 1L, "Confirmed", "BALANCED", LocalDate.now()));

        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setConcertId(1L);

        BookingResponse response = bookingService.allocateArtist(request);

        assertEquals("Confirmed", response.getBookingStatus());
        assertEquals("BALANCED", response.getStrategyApplied());
        assertNull(response.getRejectionReason());
    }

    @Test
    void allocateArtist_AvailabilityStrategy_RoleMismatch_ReturnsRejected() {
        Map<String, Object> artist  = artistData("Drummer", "Delhi", "Delhi", true, 8000);
        Map<String, Object> concert = concertData("Guitarist", "Mumbai", "Maharashtra", "AVAILABILITY", 10000);

        stubWebClients(artist, concert);
        when(bookingRepository.save(any(Booking.class)))
            .thenReturn(new Booking(2L, 1L, 1L, "Rejected", "AVAILABILITY", LocalDate.now()));

        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setConcertId(1L);

        BookingResponse response = bookingService.allocateArtist(request);

        assertEquals("Rejected", response.getBookingStatus());
        assertTrue(response.getRejectionReason().contains("Role mismatch"));
    }

    @Test
    void allocateArtist_BudgetExceeded_ReturnsRejected() {
        Map<String, Object> artist  = artistData("Keyboardist", "Chennai", "Tamil Nadu", true, 11000);
        Map<String, Object> concert = concertData("Keyboardist", "Vizag", "Andhra Pradesh", "AVAILABILITY", 10000);

        stubWebClients(artist, concert);
        when(bookingRepository.save(any(Booking.class)))
            .thenReturn(new Booking(3L, 1L, 1L, "Rejected", "AVAILABILITY", LocalDate.now()));

        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setConcertId(1L);

        BookingResponse response = bookingService.allocateArtist(request);

        assertEquals("Rejected", response.getBookingStatus());
        assertTrue(response.getRejectionReason().contains("exceeds concert budget"));
    }

    @Test
    void allocateArtist_SavesBookingToRepository() {
        Map<String, Object> artist  = artistData("Guitarist", "Bangalore", "Karnataka", true, 9000);
        Map<String, Object> concert = concertData("Guitarist", "Bangalore", "Karnataka", "BALANCED", 10000);

        stubWebClients(artist, concert);
        when(bookingRepository.save(any(Booking.class)))
            .thenReturn(new Booking(4L, 1L, 1L, "Confirmed", "BALANCED", LocalDate.now()));

        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setConcertId(1L);

        bookingService.allocateArtist(request);

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}
