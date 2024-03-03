package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.BookingStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatisticsRepository extends MongoRepository<BookingStatistics, Long> {
}
