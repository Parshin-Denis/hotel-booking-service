package com.example.booking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "users")
public class UserStatistics {

    @Id
    private Long id;

    private Instant registrationTime;

    private String name;

    private String email;
}
