package com.example.booking.mapper;

import com.example.booking.dto.BookingRequest;
import com.example.booking.dto.BookingResponse;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatistics;
import com.example.booking.model.Room;
import com.example.booking.model.User;
import com.example.booking.statistics.BookingMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
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
