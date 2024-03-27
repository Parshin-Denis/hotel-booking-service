package com.example.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class HotelRequest {
    @NotBlank(message = "Необходимо ввести название отеля")
    private String name;

    @NotBlank(message = "Необходимо ввести заголовок объявления")
    private String headline;

    @NotBlank(message = "Необходимо ввести город расположения отеля")
    private String city;

    @NotBlank(message = "Необходимо ввести адрес отеля")
    private String address;

    @PositiveOrZero(message = "Расстояние до центра не может быть отрицательным числом")
    private int distance;
}
