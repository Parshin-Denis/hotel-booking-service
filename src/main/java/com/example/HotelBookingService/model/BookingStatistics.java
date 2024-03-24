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
    private Long id;

    private Instant reservationTime;

    private Long userId;

    private LocalDate arrivalDate;

    private LocalDate departDate;

    private Long hotelId;
}
