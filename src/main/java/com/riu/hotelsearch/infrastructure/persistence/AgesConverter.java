package com.riu.hotelsearch.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class AgesConverter implements AttributeConverter<List<Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> ages) {
        try {
            return objectMapper.writeValueAsString(ages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize ages to JSON", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ages from JSON", e);
        }
    }
}
