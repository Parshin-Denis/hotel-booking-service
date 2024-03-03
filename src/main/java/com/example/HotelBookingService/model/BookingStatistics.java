package com.example.HotelBookingService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Document(collection = "bookings")
public class BookingStatistics {

    @Id
    private long id;

    private Instant reservationTime;

    private long userId;

    private LocalDate arrivalDate;

    private LocalDate departDate;

    private long hotelId;
}
