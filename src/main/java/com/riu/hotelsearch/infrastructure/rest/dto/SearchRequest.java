package com.riu.hotelsearch.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SearchRequest(

        @Schema(example = "1234")
        @NotBlank
        String hotelId,

        @Schema(example = "15/07/2024")
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @Schema(example = "20/07/2024")
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        @Schema(example = "[30, 29, 1]")
        @NotEmpty
        List<@NotNull @Min(0) Integer> ages
) {
    public SearchRequest {
        ages = ages != null ? List.copyOf(ages) : null;
    }

    @AssertTrue(message = "checkIn debe ser anterior a checkOut")
    public boolean isCheckInBeforeCheckOut() {
        return checkIn != null && checkOut != null && checkIn.isBefore(checkOut);
    }
}
