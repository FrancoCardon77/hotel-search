package com.riu.hotelsearch.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSearchConsumer {

    private final SearchRepository repository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel-search-group")
    public void consume(String message) {
        try {
            Search search = objectMapper.readValue(message, Search.class);
            repository.save(search);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize search message: {}", message, e);
        }
    }
}
