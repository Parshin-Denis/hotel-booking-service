package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends MongoRepository<UserStatistics, Long> {
}
