package com.example.HotelBookingService.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HotelFilter {
    @Positive(message = "ID должен быть положительным числом")
    private Long id;

    private String name;

    private String headline;

    private String city;

    private String address;

    @Positive(message = "Дистанция должна быть положительным числом")
    private Integer distance;

    @Min(value = 1, message = "Оценка должна быть в диапазоне от 1 до 5")
    @Max(value = 5, message = "Оценка должна быть в диапазоне от 1 до 5")
    private Double rating;

    @Positive(message = "Количество оценок должно быть положительным числом")
    private Integer ratingsAmount;
}
