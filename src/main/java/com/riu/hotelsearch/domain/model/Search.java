package com.riu.hotelsearch.domain.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Immutable domain model representing a hotel availability search.
 *
 * <p>The {@code ages} list is defensively copied on construction to guarantee
 * immutability regardless of the mutability of the list passed by the caller.</p>
 *
 * @param searchId unique identifier assigned to this search (UUID)
 * @param hotelId  identifier of the hotel being searched
 * @param checkIn  check-in date
 * @param checkOut check-out date
 * @param ages     ages of the guests; order is significant for equality and count
 */
public record Search(
        String searchId,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {
    public Search {
        ages = ages != null ? List.copyOf(ages) : null;
    }
}
