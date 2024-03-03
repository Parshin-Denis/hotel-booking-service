package com.example.HotelBookingService.service;

import com.example.HotelBookingService.dto.BookingRequest;
import com.example.HotelBookingService.dto.BookingResponse;
import com.example.HotelBookingService.exception.RequestParameterException;
import com.example.HotelBookingService.mapper.BookingMapper;
import com.example.HotelBookingService.model.Booking;
import com.example.HotelBookingService.repository.BookingRepository;
import com.example.HotelBookingService.statistics.BookingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final RoomService roomService;
    @Value("${app.kafka.bookingTopic}")
    private String topicName;
    private final KafkaTemplate<String, BookingMessage> bookingMessageKafkaTemplate;

    public BookingResponse book(String userName, BookingRequest bookingRequest) {

        if (!bookingRequest.getArrivalDate().isBefore(bookingRequest.getDepartDate())) {
            throw new RequestParameterException("Дата заезда должна наступать до даты выезда");
        }

        if (!bookingRepository.isRoomAvailable(bookingRequest.getRoomId(),
                bookingRequest.getArrivalDate(), bookingRequest.getDepartDate())) {
            throw new RequestParameterException("В указанные даты комната недоступна");
        }

        Booking newBooking = bookingRepository.save(
                bookingMapper.requestToBooking(bookingRequest,
                        roomService.getById(bookingRequest.getRoomId()), userService.findByName(userName)));

        bookingMessageKafkaTemplate.send(topicName, bookingMapper.bookingToMessage(newBooking));

        return bookingMapper.bookingToResponse(newBooking);
    }

    public List<BookingResponse> findAll(Pageable pageable) {
        return bookingMapper.bookingListToResponseList(
                bookingRepository.findAll(pageable).getContent());
    }
}
