package com.example.HotelBookingService.mapper;

import com.example.HotelBookingService.dto.BookingRequest;
import com.example.HotelBookingService.dto.BookingResponse;
import com.example.HotelBookingService.model.Booking;
import com.example.HotelBookingService.model.BookingStatistics;
import com.example.HotelBookingService.model.Room;
import com.example.HotelBookingService.model.User;
import com.example.HotelBookingService.statistics.BookingMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    Booking requestToBooking(BookingRequest bookingRequest, Room room, User user);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "room.number", target = "roomNumber")
    @Mapping(source = "room.hotel.name", target = "hotelName")
    BookingResponse bookingToResponse(Booking booking);

    List<BookingResponse> bookingListToResponseList(List<Booking> bookingList);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.hotel.id", target = "hotelId")
    BookingMessage bookingToMessage(Booking booking);

    BookingStatistics bookingMessageToStatistics(BookingMessage bookingMessage, Instant reservationTime);
}
