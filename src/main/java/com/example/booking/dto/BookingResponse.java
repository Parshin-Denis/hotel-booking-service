package com.example.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class BookingResponse {
    private long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate arrivalDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departDate;

    private String userName;

    private String hotelName;

    private String roomNumber;

    private Instant creationTime;

    private Instant updateTime;
}
