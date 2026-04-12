package com.riu.hotelsearch.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.application.ports.PersistSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSearchConsumer {

    private final PersistSearchUseCase persistSearchUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topics.searches}", groupId = "hotel-search-group")
    public void consume(String message) {
        try {
            Search search = objectMapper.readValue(message, Search.class);
            persistSearchUseCase.persist(search);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize search message: {}", message, e);
        } catch (Exception e) {
            log.error("Failed to persist search: {}", message, e);
            throw e;
        }
    }
}
