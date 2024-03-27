package com.example.booking.statistics;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingMessage {

    private long id;

    private long userId;

    private LocalDate arrivalDate;

    private LocalDate departDate;

    private long hotelId;
}
