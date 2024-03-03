package com.example.HotelBookingService.repository;

import com.example.HotelBookingService.model.Hotel;
import com.example.HotelBookingService.model.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {
    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.where(byId(hotelFilter.getId()))
                .and(byName(hotelFilter.getName()))
                .and(byHeadline(hotelFilter.getHeadline()))
                .and(byCity(hotelFilter.getCity()))
                .and(byAddress(hotelFilter.getAddress()))
                .and(byDistance(hotelFilter.getDistance()))
                .and(byRating(hotelFilter.getRating()))
                .and(byRatingsAmount(hotelFilter.getRatingsAmount()));
    }

    static Specification<Hotel> byId(Long id) {
        return (root, query, cb) -> {
            if (id == null) {
                return null;
            }
            return cb.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.like(root.get("name"), name);
        };
    }

    static Specification<Hotel> byHeadline(String headline) {
        return (root, query, cb) -> {
            if (headline == null) {
                return null;
            }
            return cb.like(root.get("headline"), headline);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null) {
                return null;
            }
            return cb.like(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, cb) -> {
            if (address == null) {
                return null;
            }
            return cb.like(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistance(Integer distance) {
        return (root, query, cb) -> {
            if (distance == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("distance"), distance);
        };
    }

    static Specification<Hotel> byRating(Double rating) {
        return (root, query, cb) -> {
            if (rating == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byRatingsAmount(Integer ratingsAmount) {
        return (root, query, cb) -> {
            if (ratingsAmount == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("ratingsAmount"), ratingsAmount);
        };
    }
}
