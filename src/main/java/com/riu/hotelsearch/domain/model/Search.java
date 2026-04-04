package com.riu.hotelsearch.domain.model;

import com.riu.hotelsearch.domain.exception.InvalidSearchDatesException;

import java.time.LocalDate;
import java.util.List;

public record Search(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {
    public Search {
        if (checkIn != null && checkOut != null && !checkIn.isBefore(checkOut)) {
            throw new InvalidSearchDatesException();
        }
        ages = ages != null ? List.copyOf(ages) : null;
    }
}
