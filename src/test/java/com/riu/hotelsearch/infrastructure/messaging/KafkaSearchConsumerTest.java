package com.riu.hotelsearch.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.application.ports.PersistSearchUseCase;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSearchConsumerTest {

    @Mock
    private PersistSearchUseCase persistSearchUseCase;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @InjectMocks
    private KafkaSearchConsumer consumer;

    private String validMessage;

    @BeforeEach
    void setUp() {
        validMessage = "{\"searchId\":\"id-1\",\"hotelId\":\"HOTEL1\",\"checkIn\":\"2024-07-15\",\"checkOut\":\"2024-07-20\",\"ages\":[30,29]}";
    }

    @Test
    void testConsume() {
        consumer.consume(validMessage);

        ArgumentCaptor<Search> captor = ArgumentCaptor.forClass(Search.class);
        verify(persistSearchUseCase).persist(captor.capture());
        Search saved = captor.getValue();

        assertAll(
                () -> assertThat(saved.searchId()).isEqualTo("id-1"),
                () -> assertThat(saved.hotelId()).isEqualTo("HOTEL1"),
                () -> assertThat(saved.checkIn()).isEqualTo(LocalDate.of(2024, 7, 15)),
                () -> assertThat(saved.checkOut()).isEqualTo(LocalDate.of(2024, 7, 20)),
                () -> assertThat(saved.ages()).isEqualTo(List.of(30, 29))
        );
    }

    @Test
    void testConsume_invalidJson() {
        consumer.consume("not-valid-json");

        verify(persistSearchUseCase, never()).persist(any());
    }

    @Test
    void testConsume_persistenceFailure() {
        doThrow(new RuntimeException("DB down")).when(persistSearchUseCase).persist(any());

        assertThrows(RuntimeException.class, () -> consumer.consume(validMessage));
    }
}
