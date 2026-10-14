package com.ragaroads.controllers;

import com.ragaroads.dtos.BookingRequest;
import com.ragaroads.dtos.BookingResponse;
import com.ragaroads.models.Booking;
import com.ragaroads.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/allocate")
    public ResponseEntity<BookingResponse> allocateArtist(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.allocateArtist(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}
