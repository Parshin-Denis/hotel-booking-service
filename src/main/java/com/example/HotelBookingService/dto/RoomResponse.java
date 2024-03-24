package com.example.HotelBookingService.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class RoomResponse {
    private long id;

    private String name;

    private String description;

    private String number;

    private double price;

    private int maxPeopleNumber;

    private HotelResponse hotel;

    private List<BookingResponse> bookings;

    private Instant creationTime;

    private Instant updateTime;
}
