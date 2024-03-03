package com.example.HotelBookingService.dto;

import com.example.HotelBookingService.model.RoleType;
import lombok.Data;

@Data
public class UserResponse {
    long id;

    private String name;

    private String password;

    private String email;

    private RoleType role;
}
