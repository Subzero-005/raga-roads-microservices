package com.ragaroads.dtos;

public class BookingResponse {

    // Booking Information
    private Long bookingId;
    private String bookingStatus;
    private String strategyApplied;
    private String rejectionReason;
    private String bookingDate;

    // Concert Information
    private Long concertId;
    private String concertTitle;
    private String concertDate;
    private String concertTime;
    private String concertCity;
    private String concertState;

    // Artist Information
    private Long artistId;
    private String artistName;
    private String artistRole;

    // Commercial Information
    private Double budgetAllocated;
    private Double artistCost;
    private Double budgetRemaining;

    public BookingResponse() {}

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getStrategyApplied() { return strategyApplied; }
    public void setStrategyApplied(String strategyApplied) { this.strategyApplied = strategyApplied; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public Long getConcertId() { return concertId; }
    public void setConcertId(Long concertId) { this.concertId = concertId; }

    public String getConcertTitle() { return concertTitle; }
    public void setConcertTitle(String concertTitle) { this.concertTitle = concertTitle; }

    public String getConcertDate() { return concertDate; }
    public void setConcertDate(String concertDate) { this.concertDate = concertDate; }

    public String getConcertTime() { return concertTime; }
    public void setConcertTime(String concertTime) { this.concertTime = concertTime; }

    public String getConcertCity() { return concertCity; }
    public void setConcertCity(String concertCity) { this.concertCity = concertCity; }

    public String getConcertState() { return concertState; }
    public void setConcertState(String concertState) { this.concertState = concertState; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getArtistRole() { return artistRole; }
    public void setArtistRole(String artistRole) { this.artistRole = artistRole; }

    public Double getBudgetAllocated() { return budgetAllocated; }
    public void setBudgetAllocated(Double budgetAllocated) { this.budgetAllocated = budgetAllocated; }

    public Double getArtistCost() { return artistCost; }
    public void setArtistCost(Double artistCost) { this.artistCost = artistCost; }

    public Double getBudgetRemaining() { return budgetRemaining; }
    public void setBudgetRemaining(Double budgetRemaining) { this.budgetRemaining = budgetRemaining; }
}
