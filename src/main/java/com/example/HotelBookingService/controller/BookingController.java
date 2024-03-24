package com.example.HotelBookingService.controller;

import com.example.HotelBookingService.dto.BookingRequest;
import com.example.HotelBookingService.dto.BookingResponse;
import com.example.HotelBookingService.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Book a room")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse book(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody @Valid BookingRequest bookingRequest) {
        return bookingService.book(userDetails.getUsername(), bookingRequest);
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<BookingResponse> findAll(Pageable pageable) {
        return bookingService.findAll(pageable);
    }
}
