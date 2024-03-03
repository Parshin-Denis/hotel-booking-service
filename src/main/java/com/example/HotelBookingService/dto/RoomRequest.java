package com.example.HotelBookingService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {
    @NotBlank(message = "Необходимо ввести название комнаты")
    private String name;

    @NotBlank(message = "Необходимо ввести описание комнаты")
    private String description;

    @NotBlank(message = "Необходимо ввести номер комнаты")
    private String number;

    @Positive(message = "Цена должна быть положительной")
    private double price;

    @Positive(message = "Максимальное количество людей должно быть положительным числом")
    private int maxPeopleNumber;

    @Positive(message = "ID отеля должен быть положительным числом")
    private long hotelId;
}
