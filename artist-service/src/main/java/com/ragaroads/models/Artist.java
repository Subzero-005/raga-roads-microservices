package com.ragaroads.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    private Long artistId;
    private String artistName;
    private String artistRole;
    private String city;
    private String state;
    private Double performanceCost;
    private Boolean available;

    public Artist() {}

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getArtistRole() { return artistRole; }
    public void setArtistRole(String artistRole) { this.artistRole = artistRole; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Double getPerformanceCost() { return performanceCost; }
    public void setPerformanceCost(Double performanceCost) { this.performanceCost = performanceCost; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
