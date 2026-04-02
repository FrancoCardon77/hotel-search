package com.riu.hotelsearch.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record SearchDetail(

        @Schema(example = "1234aBc")
        String hotelId,

        @Schema(example = "29/12/2023")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @Schema(example = "31/12/2023")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @Schema(example = "[30, 29, 1, 3]")
        List<Integer> ages
) {}
