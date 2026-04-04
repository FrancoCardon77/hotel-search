package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersistSearchServiceTest {

    @Mock
    private SearchRepository repository;

    @InjectMocks
    private PersistSearchService service;

    @Test
    void testPersist() {
        Search search = new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30));

        service.persist(search);

        verify(repository).save(search);
    }
}
