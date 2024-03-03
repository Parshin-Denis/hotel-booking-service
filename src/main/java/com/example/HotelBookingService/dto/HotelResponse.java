package com.example.HotelBookingService.dto;

import lombok.Data;

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
}
