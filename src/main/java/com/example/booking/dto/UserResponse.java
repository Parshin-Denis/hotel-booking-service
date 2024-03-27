package com.example.booking.dto;

import com.example.booking.model.RoleType;
import lombok.Data;

import java.time.Instant;

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
