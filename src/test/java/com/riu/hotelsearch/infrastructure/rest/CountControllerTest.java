package com.riu.hotelsearch.infrastructure.rest;

import com.riu.hotelsearch.domain.exception.SearchNotFoundException;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.domain.ports.in.CountUseCase;
import com.riu.hotelsearch.infrastructure.rest.dto.CountResponse;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountController.class)
class CountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountUseCase countUseCase;

    @MockBean
    private SearchMapper searchMapper;

    private String searchId;
    private Search search;
    private SearchCount searchCount;
    private CountResponse countResponse;

    @BeforeEach
    void setUp() {
        searchId = "abc-123";
        search = new Search(searchId, "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29));
        searchCount = new SearchCount(searchId, search, 3L);
        SearchDetail searchDetail = new SearchDetail("HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29));
        countResponse = new CountResponse(searchId, searchDetail, 3L);
    }

    @Test
    void testGetCount() throws Exception {
        when(countUseCase.count(searchId)).thenReturn(searchCount);
        when(searchMapper.toCountResponse(any(SearchCount.class))).thenReturn(countResponse);

        mockMvc.perform(get("/count").param("searchId", searchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(searchId))
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.search.hotelId").value("HOTEL1"));
    }

    @Test
    void testCountNotFound() throws Exception {
        String missingId = "nonexistent";
        when(countUseCase.count(missingId)).thenThrow(new SearchNotFoundException(missingId));

        mockMvc.perform(get("/count").param("searchId", missingId))
                .andExpect(status().isNotFound());
    }
}
