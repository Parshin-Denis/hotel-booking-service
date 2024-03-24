package com.example.HotelBookingService.controller;

import com.example.HotelBookingService.dto.RoomRequest;
import com.example.HotelBookingService.dto.RoomResponse;
import com.example.HotelBookingService.model.RoomFilter;
import com.example.HotelBookingService.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Create a room")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RoomResponse create(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.create(roomRequest);
    }

    @Operation(summary = "Get the room by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse findById(@PathVariable long id) {
        return roomService.findById(id);
    }

    @Operation(summary = "Modify the room by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RoomResponse update(@PathVariable long id, @RequestBody @Valid RoomRequest roomRequest) {
        return roomService.update(id, roomRequest);
    }

    @Operation(summary = "Delete the room by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        roomService.delete(id);
    }

    @Operation(summary = "Get filtered rooms")
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> filter(@Valid RoomFilter roomFilter, Pageable pageable) {
        return roomService.filter(roomFilter, pageable);
    }

}
