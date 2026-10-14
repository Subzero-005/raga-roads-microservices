package com.ragaroads.services;

import com.ragaroads.dtos.BookingRequest;
import com.ragaroads.dtos.BookingResponse;
import com.ragaroads.models.Booking;

import java.util.List;

public interface BookingService {
    BookingResponse allocateArtist(BookingRequest request);
    List<Booking> getAllBookings();
}
