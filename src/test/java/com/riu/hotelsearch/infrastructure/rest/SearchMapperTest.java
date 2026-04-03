package com.riu.hotelsearch.infrastructure.rest;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.infrastructure.rest.dto.CountResponse;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchMapperTest {

    private SearchMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new SearchMapper();
    }

    @Test
    void testToDomain() {
        SearchRequest request = new SearchRequest(
                "HOTEL1",
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 7, 20),
                List.of(30, 29)
        );

        Search result = mapper.toDomain(request);

        assertAll(
                () -> assertEquals(null, result.searchId()),
                () -> assertEquals("HOTEL1", result.hotelId()),
                () -> assertEquals(LocalDate.of(2024, 7, 15), result.checkIn()),
                () -> assertEquals(LocalDate.of(2024, 7, 20), result.checkOut()),
                () -> assertEquals(List.of(30, 29), result.ages())
        );
    }

    @Test
    void testToCountResponse() {
        Search search = new Search("abc-123", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29));
        SearchCount searchCount = new SearchCount("abc-123", search, 5L);

        CountResponse result = mapper.toCountResponse(searchCount);

        assertAll(
                () -> assertEquals("abc-123", result.searchId()),
                () -> assertEquals(5L, result.count()),
                () -> assertEquals("HOTEL1", result.search().hotelId()),
                () -> assertEquals(LocalDate.of(2024, 7, 15), result.search().checkIn()),
                () -> assertEquals(LocalDate.of(2024, 7, 20), result.search().checkOut()),
                () -> assertEquals(List.of(30, 29), result.search().ages())
        );
    }
}
