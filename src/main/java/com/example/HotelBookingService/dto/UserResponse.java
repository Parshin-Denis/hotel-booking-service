package com.example.HotelBookingService.dto;

import com.example.HotelBookingService.model.RoleType;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class UserResponse {
    private long id;

    private String name;

    private String password;

    private String email;

    private RoleType role;

    private Instant creationTime;

    private Instant updateTime;
}
