package com.example.HotelBookingService.model;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomFilter {
    @Positive(message = "ID номера должен быть положительным числом")
    private Long id;

    private String name;

    @Positive(message = "Цена должна быть положительным числом")
    private Double minPrice;

    @Positive(message = "Цена должна быть положительным числом")
    private Double maxPrice;

    @Positive(message = "Количество людей должно быть положительным числом")
    private Integer maxPeopleNumber;

    private LocalDate arrivalDate;

    private LocalDate departDate;

    @Positive(message = "ID отеля должен быть положительным числом")
    private Long hotelId;
}
