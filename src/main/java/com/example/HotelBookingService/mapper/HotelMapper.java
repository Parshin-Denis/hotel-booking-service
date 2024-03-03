package com.example.HotelBookingService.mapper;

import com.example.HotelBookingService.dto.HotelListResponse;
import com.example.HotelBookingService.dto.HotelNonEntryData;
import com.example.HotelBookingService.dto.HotelRequest;
import com.example.HotelBookingService.dto.HotelResponse;
import com.example.HotelBookingService.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(HotelRequest hotelRequest);

    Hotel requestToHotel(HotelRequest hotelRequest, HotelNonEntryData hotelNonEntryData);

    HotelNonEntryData hotelToNonEntryData(Hotel hotel);

    HotelResponse hotelToResponse(Hotel hotel);

    List<HotelResponse> hotelListToResponseList(List<Hotel> hotelList);

    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotelList) {
        HotelListResponse hotelListResponse = new HotelListResponse();
        hotelListResponse.setHotelResponseList(hotelListToResponseList(hotelList));
        hotelListResponse.setHotelsAmount(hotelList.size());
        return hotelListResponse;
    }
}
