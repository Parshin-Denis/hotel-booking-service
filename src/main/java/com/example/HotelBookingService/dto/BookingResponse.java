package com.example.HotelBookingService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate arrivalDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate departDate;

    String userName;

    String hotelName;

    String roomNumber;
}
