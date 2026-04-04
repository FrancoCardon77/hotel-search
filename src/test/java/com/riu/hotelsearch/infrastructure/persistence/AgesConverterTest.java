package com.riu.hotelsearch.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgesConverterTest {

    private AgesConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AgesConverter();
    }

    @Test
    void testConvertToDatabaseColumn() {
        String result = converter.convertToDatabaseColumn(List.of(30, 29, 1));
        assertEquals("[30,29,1]", result);
    }

    @Test
    void testConvertToDatabaseColumn_empty() {
        String result = converter.convertToDatabaseColumn(List.of());
        assertEquals("[]", result);
    }

    @Test
    void testConvertToEntityAttribute() {
        List<Integer> result = converter.convertToEntityAttribute("[30,29,1]");
        assertEquals(List.of(30, 29, 1), result);
    }

    @Test
    void testConvertToEntityAttribute_empty() {
        List<Integer> result = converter.convertToEntityAttribute("[]");
        assertEquals(List.of(), result);
    }

    @Test
    void testConvertToEntityAttribute_invalidJson() {
        assertThrows(RuntimeException.class, () -> converter.convertToEntityAttribute("not-json"));
    }
}
