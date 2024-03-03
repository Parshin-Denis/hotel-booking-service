package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    boolean existsByNumberAndHotelId(String number, long hotelId);

    @Query("SELECT DISTINCT r.id" +
            " FROM rooms r JOIN bookings b" +
            " WHERE" +
            " ((:arrivalDate <= b.arrivalDate AND b.arrivalDate < :departDate) OR" +
            " (:arrivalDate < b.departDate AND b.departDate <= :departDate) OR" +
            " (b.arrivalDate < :arrivalDate AND :departDate < b.departDate))")
    List<Long> getUnavailableRoomIds(LocalDate arrivalDate, LocalDate departDate);
}
