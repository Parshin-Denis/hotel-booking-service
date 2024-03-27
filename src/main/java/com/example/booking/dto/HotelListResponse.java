package com.example.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelListResponse {

    private int hotelsAmount;

    private List<HotelResponse> hotelResponseList;
}
