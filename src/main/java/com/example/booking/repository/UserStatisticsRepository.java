package com.example.booking.repository;

import com.example.booking.model.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends MongoRepository<UserStatistics, Long> {
}
