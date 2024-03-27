package com.example.booking.repository;

import com.example.booking.model.BookingStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatisticsRepository extends MongoRepository<BookingStatistics, Long> {
}
