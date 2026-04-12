package com.riu.hotelsearch.infrastructure.rest;

import com.riu.hotelsearch.application.ports.SearchUseCase;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchRequest;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search")
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchUseCase searchUseCase;
    private final SearchMapper searchMapper;

    @Operation(summary = "Registra una búsqueda de disponibilidad hotelera")
    @ApiResponse(responseCode = "200", description = "ID de búsqueda generado")
    @ApiResponse(responseCode = "400", description = "Payload inválido")
    @PostMapping
    public ResponseEntity<SearchResponse> search(@Valid @RequestBody SearchRequest request) {
        String searchId = searchUseCase.search(searchMapper.toDomain(request));
        return ResponseEntity.ok(new SearchResponse(searchId));
    }
}
