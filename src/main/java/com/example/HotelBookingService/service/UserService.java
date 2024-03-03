package com.example.HotelBookingService.service;

import com.example.HotelBookingService.dto.UserRequest;
import com.example.HotelBookingService.dto.UserResponse;
import com.example.HotelBookingService.exception.RequestParameterException;
import com.example.HotelBookingService.mapper.UserMapper;
import com.example.HotelBookingService.model.User;
import com.example.HotelBookingService.repository.UserRepository;
import com.example.HotelBookingService.statistics.UserMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.kafka.userTopic}")
    private String topicName;

    private final KafkaTemplate<String, UserMessage> userMessageKafkaTemplate;

    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByNameAndEmail(userRequest.getName(), userRequest.getEmail())) {
            throw new RequestParameterException(
                    MessageFormat.format("Пользователь с именем {0} и почтой {1] уже существует",
                            userRequest.getName(), userRequest.getEmail())
            );
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User newUser = userRepository.save(
                        userMapper.requestToUser(userRequest));

        userMessageKafkaTemplate.send(topicName, userMapper.userToMessage(newUser));

        return userMapper.userToResponse(newUser);
    }

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format("Пользователь с ID {0} не найден", id)
                )
        );
    }

    public UserResponse findById(long id) {
        return userMapper.userToResponse(getById(id));
    }

    public UserResponse update(long id, UserRequest userRequest) {
        User existedUser = getById(id);
        User updatedUser = userMapper.requestToUser(userRequest,
                userMapper.userToNonEntryData(existedUser));
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userMapper.userToResponse(
                userRepository.save(updatedUser)
        );
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format("Пользователь с именем {0} не найден", name)
                )
        );
    }

}
