package com.ragaroads.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    private Long concertId;
    private String concertTitle;
    private String concertDate;
    private String concertTime;
    private String concertCity;
    private String concertState;
    private String strategyType;
    private String artistRole;
    private Double budgetAllocated;

    public Concert() {}

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

    public String getStrategyType() { return strategyType; }
    public void setStrategyType(String strategyType) { this.strategyType = strategyType; }

    public String getArtistRole() { return artistRole; }
    public void setArtistRole(String artistRole) { this.artistRole = artistRole; }

    public Double getBudgetAllocated() { return budgetAllocated; }
    public void setBudgetAllocated(Double budgetAllocated) { this.budgetAllocated = budgetAllocated; }
}
