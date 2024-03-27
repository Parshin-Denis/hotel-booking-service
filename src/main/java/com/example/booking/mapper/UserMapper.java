package com.example.booking.mapper;

import com.example.booking.dto.UserNonEntryData;
import com.example.booking.dto.UserRequest;
import com.example.booking.dto.UserResponse;
import com.example.booking.model.User;
import com.example.booking.model.UserStatistics;
import com.example.booking.statistics.UserMessage;
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
