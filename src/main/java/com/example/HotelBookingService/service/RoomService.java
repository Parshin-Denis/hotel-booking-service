package com.example.HotelBookingService.service;

import com.example.HotelBookingService.dto.RoomRequest;
import com.example.HotelBookingService.dto.RoomResponse;
import com.example.HotelBookingService.exception.RequestParameterException;
import com.example.HotelBookingService.mapper.RoomMapper;
import com.example.HotelBookingService.model.Hotel;
import com.example.HotelBookingService.model.Room;
import com.example.HotelBookingService.model.RoomFilter;
import com.example.HotelBookingService.repository.RoomRepository;
import com.example.HotelBookingService.repository.RoomSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelService hotelService;
    private final RoomMapper roomMapper;

    @Transactional
    public RoomResponse create(RoomRequest roomRequest) {
        checkRoomNumber(roomRequest);
        Hotel hotel = hotelService.getById(roomRequest.getHotelId());
        Room newRoom = roomMapper.requestToRoom(roomRequest);
        newRoom.setHotel(hotel);
        return roomMapper.roomToResponse(roomRepository.save(newRoom));
    }

    public Room getById(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Комната с ID {0} не найдена", id))
                );
    }

    public RoomResponse findById(long id) {
        return roomMapper.roomToResponse(getById(id));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public RoomResponse update(long id, RoomRequest roomRequest) {
        checkRoomNumber(roomRequest);
        Room existedRoom = getById(id);
        Room updatedRoom = roomMapper.requestToRoom(roomRequest,
                roomMapper.roomToNonEntryData(existedRoom));
        Hotel hotel = hotelService.getById(roomRequest.getHotelId());
        updatedRoom.setHotel(hotel);
        return roomMapper.roomToResponse(roomRepository.save(updatedRoom));
    }

    @Transactional
    public void delete(long id) {
        roomRepository.deleteById(id);
    }

    public void checkRoomNumber(RoomRequest roomRequest) {
        if (roomRepository.existsByNumberAndHotelId(roomRequest.getNumber(), roomRequest.getHotelId())) {
            throw new RequestParameterException(
                    MessageFormat.format("Комната с номером {0} уже существует в этом отеле",
                            roomRequest.getNumber())
            );
        }
    }

    public List<RoomResponse> filter(RoomFilter roomFilter, Pageable pageable) {
        List<Long> unavailableRoomIds = new ArrayList<>();
        if (roomFilter.getArrivalDate() != null && roomFilter.getDepartDate() != null) {
            unavailableRoomIds = roomRepository.getUnavailableRoomIds(roomFilter.getArrivalDate(),
                    roomFilter.getDepartDate());
        }
        return roomRepository.findAll(RoomSpecification.withFilter(roomFilter, unavailableRoomIds), pageable)
                .getContent()
                .stream()
                .map(roomMapper::roomToResponse)
                .toList();
    }
}
