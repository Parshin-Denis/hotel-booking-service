package com.example.booking.repository;

import com.example.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT case when COUNT(*) = 0 then true else false end" +
            " FROM bookings b" +
            " WHERE b.room.id = :roomId AND" +
            " ((:arrivalDate <= b.arrivalDate AND b.arrivalDate < :departDate) OR" +
            " (:arrivalDate < b.departDate AND b.departDate <= :departDate) OR" +
            " (b.arrivalDate < :arrivalDate AND :departDate < b.departDate))")
    boolean isRoomAvailable(long roomId, LocalDate arrivalDate, LocalDate departDate);

    @Modifying
    @Query("DELETE from bookings b WHERE b.user.id = :userId")
    void deleteAllByUserId(long userId);
}
