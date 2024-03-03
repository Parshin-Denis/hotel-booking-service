package com.example.HotelBookingService.mapper;

import com.example.HotelBookingService.dto.UserNonEntryData;
import com.example.HotelBookingService.dto.UserRequest;
import com.example.HotelBookingService.dto.UserResponse;
import com.example.HotelBookingService.model.User;
import com.example.HotelBookingService.model.UserStatistics;
import com.example.HotelBookingService.statistics.UserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserRequest userRequest);

    User requestToUser(UserRequest userRequest, UserNonEntryData userNonEntryData);

    UserNonEntryData userToNonEntryData(User user);

    UserResponse userToResponse(User user);

    UserMessage userToMessage(User user);

    UserStatistics userMessageToStatistics(UserMessage userMessage, Instant registrationTime);
}
