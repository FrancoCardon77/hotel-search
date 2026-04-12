package com.riu.hotelsearch.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchEventPublisher;
import com.riu.hotelsearch.infrastructure.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSearchPublisher implements SearchEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaProperties kafkaProperties;

    @Override
    public void publish(Search search) {
        try {
            String payload = objectMapper.writeValueAsString(search);
            kafkaTemplate.send(kafkaProperties.topics().searches(), search.searchId(), payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize search event", e);
        }
    }
}
