package com.example.HotelBookingService.controller;

import com.example.HotelBookingService.dto.HotelListResponse;
import com.example.HotelBookingService.dto.HotelRequest;
import com.example.HotelBookingService.dto.HotelResponse;
import com.example.HotelBookingService.model.HotelFilter;
import com.example.HotelBookingService.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Create a hotel")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public HotelResponse create(@RequestBody @Valid HotelRequest hotelRequest){
        return hotelService.create(hotelRequest);
    }

    @Operation(summary = "Get the hotel by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse findById(@PathVariable long id) {
        return hotelService.findById(id);
    }

    @Operation(summary = "Modify the hotel by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public HotelResponse update(@PathVariable long id, @RequestBody @Valid HotelRequest hotelRequest) {
        return hotelService.update(id, hotelRequest);
    }

    @Operation(summary = "Delete the hotel by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        hotelService.delete(id);
    }

    @Operation(summary = "Get all hotels")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HotelListResponse findAll(Pageable pageable) {
        return hotelService.findAll(pageable);
    }

    @Operation(summary = "Rate the hotel")
    @GetMapping("/rate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse rate(@PathVariable long id, @RequestParam int mark) {
        return hotelService.rate(id, mark);
    }

    @Operation(summary = "Get filtered hotels")
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public HotelListResponse filter(@Valid HotelFilter hotelFilter, Pageable pageable) {
        return hotelService.filter(hotelFilter, pageable);
    }

}
