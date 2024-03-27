package com.example.booking.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class HotelResponse {
    private long id;

    private String name;

    private String headline;

    private String city;

    private String address;

    private int distance;

    private double rating;

    private int ratingsAmount;

    private Instant creationTime;

    private Instant updateTime;
}
