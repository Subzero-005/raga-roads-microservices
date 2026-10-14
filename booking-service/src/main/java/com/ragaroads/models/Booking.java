package com.ragaroads.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private Long concertId;
    private Long artistId;
    private String bookingStatus;
    private String strategyApplied;
    private LocalDate bookingDate;

    public Booking() {}

    public Booking(Long bookingId, Long concertId, Long artistId,
                   String bookingStatus, String strategyApplied, LocalDate bookingDate) {
        this.bookingId = bookingId;
        this.concertId = concertId;
        this.artistId = artistId;
        this.bookingStatus = bookingStatus;
        this.strategyApplied = strategyApplied;
        this.bookingDate = bookingDate;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getConcertId() { return concertId; }
    public void setConcertId(Long concertId) { this.concertId = concertId; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getStrategyApplied() { return strategyApplied; }
    public void setStrategyApplied(String strategyApplied) { this.strategyApplied = strategyApplied; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}
