package com.example.HotelBookingService.controller;

import com.example.HotelBookingService.dto.UserRequest;
import com.example.HotelBookingService.dto.UserResponse;
import com.example.HotelBookingService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Create a user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @Operation(summary = "Get the user by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public UserResponse findById(@PathVariable long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Modify the user by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse update(@PathVariable long id, @RequestBody @Valid UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @Operation(summary = "Delete the user by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
