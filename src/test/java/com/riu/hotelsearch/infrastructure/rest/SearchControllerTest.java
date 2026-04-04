package com.riu.hotelsearch.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.exception.InvalidSearchDatesException;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.in.SearchUseCase;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SearchUseCase searchUseCase;

    @MockBean
    private SearchMapper searchMapper;

    private Map<String, Object> validBody;

    @BeforeEach
    void setUp() {
        validBody = Map.of(
                "hotelId", "HOTEL1",
                "checkIn", "15/07/2024",
                "checkOut", "20/07/2024",
                "ages", List.of(30, 29, 1)
        );
    }

    @Test
    void testSearch_200() throws Exception {
        when(searchMapper.toDomain(any(SearchRequest.class)))
                .thenReturn(new Search(null, "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29, 1)));
        when(searchUseCase.search(any())).thenReturn("abc123");

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("abc123"));
    }

    @Test
    void testBlankHotelId() throws Exception {
        Map<String, Object> body = Map.of(
                "hotelId", "",
                "checkIn", "15/07/2024",
                "checkOut", "20/07/2024",
                "ages", List.of(30)
        );

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearch_wrongDateFormat() throws Exception {
        Map<String, Object> body = Map.of(
                "hotelId", "HOTEL1",
                "checkIn", "2024-07-15",
                "checkOut", "2024-07-20",
                "ages", List.of(30)
        );

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearchEmptyAges() throws Exception {
        Map<String, Object> body = Map.of(
                "hotelId", "HOTEL1",
                "checkIn", "15/07/2024",
                "checkOut", "20/07/2024",
                "ages", List.of()
        );

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearchNullAges() throws Exception {
        String body = "{\"hotelId\":\"HOTEL1\",\"checkIn\":\"15/07/2024\",\"checkOut\":\"20/07/2024\",\"ages\":null}";

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckInAfterCheckOut() throws Exception {
        Map<String, Object> body = Map.of(
                "hotelId", "HOTEL1",
                "checkIn", "25/07/2024",
                "checkOut", "20/07/2024",
                "ages", List.of(30)
        );

        when(searchMapper.toDomain(any(SearchRequest.class)))
                .thenThrow(new InvalidSearchDatesException());

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearch_500() throws Exception {
        when(searchMapper.toDomain(any(SearchRequest.class)))
                .thenReturn(new Search(null, "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30)));
        when(searchUseCase.search(any())).thenThrow(new RuntimeException("unexpected"));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBody)))
                .andExpect(status().isInternalServerError());
    }
}
