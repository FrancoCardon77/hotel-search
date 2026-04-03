package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchEventPublisher publisher;

    @InjectMocks
    private SearchService searchService;

    private LocalDate checkIn;
    private LocalDate checkOut;

    @BeforeEach
    void setUp() {
        checkIn = LocalDate.of(2024, 7, 15);
        checkOut = LocalDate.of(2024, 7, 20);
    }

    @Test
    void testPublishesToKafka() {
        Search input = new Search(null, "HOTEL1", checkIn, checkOut, List.of(30, 29, 1));

        searchService.search(input);

        ArgumentCaptor<Search> captor = ArgumentCaptor.forClass(Search.class);
        verify(publisher).publish(captor.capture());
        Search published = captor.getValue();

        assertThat(published.searchId()).isNotNull();
        assertThat(published.hotelId()).isEqualTo("HOTEL1");
        assertThat(published.checkIn()).isEqualTo(checkIn);
        assertThat(published.checkOut()).isEqualTo(checkOut);
        assertThat(published.ages()).isEqualTo(List.of(30, 29, 1));
    }

    @Test
    void testReturnValidId() {
        Search input = new Search(null, "HOTEL2", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 5), List.of(25));

        String searchId = searchService.search(input);

        assertThat(searchId).isNotNull().isNotBlank();
    }

    @Test
    void testIdConsistency() {
        Search input = new Search(null, "HOTEL1", checkIn, checkOut, List.of(30));
        ArgumentCaptor<Search> captor = ArgumentCaptor.forClass(Search.class);

        String returned = searchService.search(input);

        verify(publisher).publish(captor.capture());
        assertThat(returned).isEqualTo(captor.getValue().searchId());
    }
}
