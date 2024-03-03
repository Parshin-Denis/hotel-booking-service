package com.example.HotelBookingService.statistics;

import com.example.HotelBookingService.mapper.BookingMapper;
import com.example.HotelBookingService.mapper.UserMapper;
import com.example.HotelBookingService.repository.BookingStatisticsRepository;
import com.example.HotelBookingService.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;
    private final UserMapper userMapper;
    private final BookingStatisticsRepository bookingStatisticsRepository;
    private final BookingMapper bookingMapper;
    @Value("${app.statistics.userFile}")
    private String userStatisticsFile;
    @Value("${app.statistics.bookingFile}")
    private String bookingStatisticsFile;

    public void saveUserStatistics(UserMessage userMessage, long timestamp) {
        userStatisticsRepository.save(
                userMapper.userMessageToStatistics(userMessage, Instant.ofEpochMilli(timestamp))
        );
    }

    public void saveBookingStatistics(BookingMessage bookingMessage, long timestamp) {
        bookingStatisticsRepository.save(
                bookingMapper.bookingMessageToStatistics(bookingMessage, Instant.ofEpochMilli(timestamp))
        );
    }

    public String download() {
        List<String> userLines = new ArrayList<>();
        userLines.add("ID,Время регистрации,Имя,Электронная почта");
        userStatisticsRepository.findAll()
                .forEach(u -> userLines.add(u.getId() + "," + u.getRegistrationTime() +
                        "," + u.getName() + "," + u.getEmail()));

        List<String> bookingLines = new ArrayList<>();
        bookingLines.add("ID,Время бронирования,ID пользователя,Время заезда,Время выезда,ID отеля");
        bookingStatisticsRepository.findAll()
                .forEach(b -> bookingLines.add(b.getId() + "," + b.getReservationTime() + "," +
                        b.getUserId() + "," + b.getArrivalDate() + "," + b.getDepartDate() + "," + b.getHotelId()));

        try {
            Files.write(Path.of(userStatisticsFile), userLines);
            Files.write(Path.of(bookingStatisticsFile), bookingLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Данные выгружены";
    }
}
