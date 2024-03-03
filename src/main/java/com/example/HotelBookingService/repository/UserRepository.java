package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    boolean existsByNameAndEmail(String name, String email);
}
