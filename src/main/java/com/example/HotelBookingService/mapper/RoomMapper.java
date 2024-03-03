package com.example.HotelBookingService.mapper;

import com.example.HotelBookingService.dto.RoomNonEntryData;
import com.example.HotelBookingService.dto.RoomRequest;
import com.example.HotelBookingService.dto.RoomResponse;
import com.example.HotelBookingService.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {HotelMapper.class, BookingMapper.class})
public interface RoomMapper {

    Room requestToRoom(RoomRequest roomRequest);

    Room requestToRoom(RoomRequest roomRequest, RoomNonEntryData roomNonEntryData);

    RoomNonEntryData roomToNonEntryData(Room room);

    RoomResponse roomToResponse(Room room);
}
