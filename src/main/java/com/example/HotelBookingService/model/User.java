package com.example.HotelBookingService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;

    private String password;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleType role;

}
