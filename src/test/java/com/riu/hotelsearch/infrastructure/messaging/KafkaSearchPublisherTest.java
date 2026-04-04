package com.riu.hotelsearch.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.infrastructure.config.KafkaProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaSearchPublisherTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KafkaProperties kafkaProperties;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @InjectMocks
    private KafkaSearchPublisher publisher;

    private Search search;

    @BeforeEach
    void setUp() {
        KafkaProperties.Topics topics = new KafkaProperties.Topics();
        topics.setSearches("hotel_availability_searches");
        when(kafkaProperties.getTopics()).thenReturn(topics);
        search = new Search("id-1", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30, 29));
    }

    @Test
    void testPublish() {
        publisher.publish(search);

        verify(kafkaTemplate).send(eq("hotel_availability_searches"), any(), any());
    }

    @Test
    void testKeyIsSearchId() {
        Search searchWithId = new Search("id-abc", "HOTEL1", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), List.of(30));

        publisher.publish(searchWithId);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(any(), keyCaptor.capture(), any());
        assertThat(keyCaptor.getValue()).isEqualTo("id-abc");
    }
}
