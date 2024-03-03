package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.Room;
import com.example.HotelBookingService.model.RoomFilter;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface RoomSpecification {
    static Specification<Room> withFilter(RoomFilter roomFilter, List<Long> unavailableRoomIds) {
        return Specification.where(byId(roomFilter.getId()))
                .and(byName(roomFilter.getName()))
                .and(byPriceRange(roomFilter.getMinPrice(), roomFilter.getMaxPrice()))
                .and(byPeopleNumber(roomFilter.getMaxPeopleNumber()))
                .and(byHotelId(roomFilter.getHotelId()))
                .and(byStayingDates(unavailableRoomIds));
    }

    static Specification<Room> byId(Long id) {
        return (root, query, cb) -> {
            if (id == null) {
                return null;
            }
            return cb.equal(root.get("id"), id);
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.like(root.get("name"), name);
        };
    }

    static Specification<Room> byPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }

            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return cb.between(root.get("price"), minPrice, maxPrice);
        };
    }

    static Specification<Room> byPeopleNumber(Integer peopleNumber) {
        return (root, query, cb) -> {
            if (peopleNumber == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("maxPeopleNumber"), peopleNumber);
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }
            return cb.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    static Specification<Room> byStayingDates(List<Long> unavailableRoomIds) {
        return (root, query, cb) -> {
            if (unavailableRoomIds.size() == 0) {
                return null;
            }
            return root.get("id").in(unavailableRoomIds).not();
        };
    }

}
