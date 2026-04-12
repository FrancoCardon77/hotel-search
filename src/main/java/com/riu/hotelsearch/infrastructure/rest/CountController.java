package com.riu.hotelsearch.infrastructure.rest;

import com.riu.hotelsearch.application.ports.CountUseCase;
import com.riu.hotelsearch.infrastructure.rest.dto.CountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Count")
@RestController
@RequestMapping("/count")
@RequiredArgsConstructor
public class CountController {

    private final CountUseCase countUseCase;
    private final SearchMapper searchMapper;

    @Operation(summary = "Devuelve cuántas veces se realizó una búsqueda idéntica")
    @ApiResponse(responseCode = "200", description = "Resultado con el conteo")
    @ApiResponse(responseCode = "404", description = "searchId no encontrado")
    @GetMapping
    public ResponseEntity<CountResponse> count(@RequestParam String searchId) {
        return ResponseEntity.ok(searchMapper.toCountResponse(countUseCase.count(searchId)));
    }
}
