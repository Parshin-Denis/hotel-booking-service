package com.example.booking.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserNonEntryData {

    private long id;

    private Instant creationTime;

}
