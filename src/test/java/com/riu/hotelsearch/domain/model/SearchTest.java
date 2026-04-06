package com.riu.hotelsearch.domain.model;

import com.riu.hotelsearch.domain.exception.InvalidSearchDatesException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchTest {

    @Test
    void testValidSearch() {
        Search search = new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30));
        assertEquals("id-1", search.searchId());
        assertEquals(List.of(30), search.ages());
    }

    @Test
    void testNullCheckIn() {
        assertDoesNotThrow(() -> new Search("id-1", "HOTEL1", null, LocalDate.of(2024, 7, 20), List.of(30)));
    }

    @Test
    void testNullCheckOut() {
        assertDoesNotThrow(() -> new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), null, List.of(30)));
    }

    @Test
    void testCheckInAfterCheckOut() {
        assertThrows(InvalidSearchDatesException.class, () ->
                new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 20), LocalDate.of(2024, 7, 15), List.of(30)));
    }

    @Test
    void testCheckInEqualCheckOut() {
        assertThrows(InvalidSearchDatesException.class, () ->
                new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 15), List.of(30)));
    }

    @Test
    void testNullAges() {
        Search search = new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), null);
        assertNull(search.ages());
    }
}
