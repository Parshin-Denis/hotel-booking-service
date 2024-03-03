package com.example.HotelBookingService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Введенная дата заезда уже наступила")
    LocalDate arrivalDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Введенная дата выезда уже наступила")
    LocalDate departDate;

    @Positive(message = "ID комнаты должен быть положительным числом")
    long roomId;
}