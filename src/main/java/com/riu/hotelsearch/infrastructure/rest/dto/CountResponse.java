package com.riu.hotelsearch.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CountResponse(

        @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
        String searchId,

        SearchDetail search,

        @Schema(example = "3")
        long count
) {}
