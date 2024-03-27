package com.example.booking.mapper;

import com.example.booking.dto.RoomNonEntryData;
import com.example.booking.dto.RoomRequest;
import com.example.booking.dto.RoomResponse;
import com.example.booking.model.Room;
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
