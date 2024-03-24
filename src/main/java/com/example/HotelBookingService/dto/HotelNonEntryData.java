package com.example.HotelBookingService.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class HotelNonEntryData {
    private long id;

    private double rating;

    private int ratingsAmount;

    private Instant creationTime;
}
