package com.example.booking.service;

import com.example.booking.dto.HotelListResponse;
import com.example.booking.dto.HotelRequest;
import com.example.booking.dto.HotelResponse;
import com.example.booking.exception.RequestParameterException;
import com.example.booking.mapper.HotelMapper;
import com.example.booking.model.Hotel;
import com.example.booking.model.HotelFilter;
import com.example.booking.repository.HotelRepository;
import com.example.booking.repository.HotelSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional
    public HotelResponse create(HotelRequest hotelRequest) {
        return hotelMapper.hotelToResponse(
                hotelRepository.save(
                        hotelMapper.requestToHotel(hotelRequest)
                )
        );
    }

    public Hotel getById(long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Отель с ID {0} не найден", id))
                );
    }

    public HotelResponse findById(long id) {
        return hotelMapper.hotelToResponse(getById(id));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public HotelResponse update(long id, HotelRequest hotelRequest) {
        Hotel existedHotel = getById(id);
        Hotel updatedHotel = hotelMapper.requestToHotel(hotelRequest,
                hotelMapper.hotelToNonEntryData(existedHotel));
        return hotelMapper.hotelToResponse(hotelRepository.save(updatedHotel));
    }

    @Transactional
    public void delete(long id) {
        hotelRepository.deleteById(id);
    }

    public HotelListResponse findAll(Pageable pageable) {
        return hotelMapper.hotelListToHotelListResponse(hotelRepository.findAll(pageable).getContent());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public HotelResponse rate(long id, int mark) {
        if (mark < 1 || mark > 5) {
            throw new RequestParameterException("Оценка должна быть в диапазоне от 1 до 5");
        }
        Hotel hotel = getById(id);
        if (hotel.getRatingsAmount() == 0) {
            hotel.setRating((double) mark);
            hotel.setRatingsAmount(1);
        } else {
            double totalRating = hotel.getRating() * hotel.getRatingsAmount() - hotel.getRating() + mark;

            hotel.setRating(
                    (double) Math.round(totalRating / hotel.getRatingsAmount() * 10) / 10
            );
            hotel.setRatingsAmount(hotel.getRatingsAmount() + 1);
        }
        return hotelMapper.hotelToResponse(hotelRepository.save(hotel));
    }

    public HotelListResponse filter(HotelFilter hotelFilter, Pageable pageable) {
        return hotelMapper.hotelListToHotelListResponse(
                hotelRepository.findAll(HotelSpecification.withFilter(hotelFilter), pageable).getContent());
    }
}
