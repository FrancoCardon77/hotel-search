package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.exception.SearchNotFoundException;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountServiceTest {

    @Mock
    private SearchRepository repository;

    @InjectMocks
    private CountService countService;

    private Search search;
    private String searchId;

    @BeforeEach
    void setUp() {
        searchId = "abc-123";
        search = new Search(searchId, "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29));
    }

    @Test
    void testCount() {
        when(repository.findById(searchId)).thenReturn(Optional.of(search));
        when(repository.countBySearch(search)).thenReturn(5L);

        SearchCount result = countService.count(searchId);

        assertThat(result.searchId()).isEqualTo(searchId);
        assertThat(result.search()).isEqualTo(search);
        assertThat(result.count()).isEqualTo(5L);
    }

    @Test
    void testThrowsWhenNotFound() {
        String missingId = "nonexistent";
        when(repository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> countService.count(missingId))
                .isInstanceOf(SearchNotFoundException.class)
                .hasMessageContaining(missingId);
    }
}
