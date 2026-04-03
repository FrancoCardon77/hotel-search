package com.riu.hotelsearch.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryAdapterTest {

    @Mock
    private SearchJpaRepository jpaRepository;

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private SearchRepositoryAdapter adapter;

    private LocalDate checkIn;
    private LocalDate checkOut;

    @BeforeEach
    void setUp() {
        checkIn = LocalDate.of(2024, 7, 15);
        checkOut = LocalDate.of(2024, 7, 20);
    }

    @Test
    void testSave() {
        Search search = new Search("id-1", "HOTEL1", checkIn, checkOut, List.of(30, 29));

        adapter.save(search);

        ArgumentCaptor<SearchEntity> captor = ArgumentCaptor.forClass(SearchEntity.class);
        verify(jpaRepository).save(captor.capture());
        SearchEntity saved = captor.getValue();

        assertAll(
                () -> assertThat(saved.getSearchId()).isEqualTo("id-1"),
                () -> assertThat(saved.getHotelId()).isEqualTo("HOTEL1"),
                () -> assertThat(saved.getCheckIn()).isEqualTo(checkIn),
                () -> assertThat(saved.getCheckOut()).isEqualTo(checkOut),
                () -> assertThat(saved.getAges()).isEqualTo(List.of(30, 29))
        );
    }

    @Test
    void testFindById() {
        SearchEntity entity = new SearchEntity("id-1", "HOTEL1", checkIn, checkOut, List.of(30));
        when(jpaRepository.findById("id-1")).thenReturn(Optional.of(entity));

        Optional<Search> result = adapter.findById("id-1");

        assertAll(
                () -> assertThat(result).isPresent(),
                () -> assertThat(result.get().searchId()).isEqualTo("id-1"),
                () -> assertThat(result.get().hotelId()).isEqualTo("HOTEL1")
        );
    }

    @Test
    void testFindByIdNotFound() {
        when(jpaRepository.findById("nonexistent")).thenReturn(Optional.empty());

        Optional<Search> result = adapter.findById("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void testCountBySearch() {
        Search search = new Search("id-1", "HOTEL1", checkIn, checkOut, List.of(30, 29));
        when(jpaRepository.countBySearchFields(any(), any(), any(), any())).thenReturn(4L);

        long count = adapter.countBySearch(search);

        assertAll(
                () -> assertThat(count).isEqualTo(4L),
                () -> verify(jpaRepository).countBySearchFields("HOTEL1", checkIn, checkOut, "[30,29]")
        );
    }
}
