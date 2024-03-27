package com.example.booking.service;

import com.example.booking.dto.UserRequest;
import com.example.booking.dto.UserResponse;
import com.example.booking.exception.RequestParameterException;
import com.example.booking.mapper.UserMapper;
import com.example.booking.model.User;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.statistics.UserMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;
    @Value("${app.kafka.userTopic}")
    private String topicName;

    private final KafkaTemplate<String, UserMessage> userMessageKafkaTemplate;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByNameAndEmail(userRequest.getName(), userRequest.getEmail())) {
            throw new RequestParameterException(
                    MessageFormat.format("Пользователь с именем {0} и почтой {1} уже существует",
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponse update(long id, UserRequest userRequest) {
        User existedUser = getById(id);
        User updatedUser = userMapper.requestToUser(userRequest,
                userMapper.userToNonEntryData(existedUser));
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userMapper.userToResponse(
                userRepository.save(updatedUser)
        );
    }

    @Transactional
    public void delete(long id) {
        bookingRepository.deleteAllByUserId(id);
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
