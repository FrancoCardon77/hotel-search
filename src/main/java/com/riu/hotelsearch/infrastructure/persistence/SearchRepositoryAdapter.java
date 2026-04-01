package com.riu.hotelsearch.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SearchRepositoryAdapter implements SearchRepository {

    private final SearchJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(Search search) {
        jpaRepository.save(toEntity(search));
    }

    @Override
    public Optional<Search> findById(String searchId) {
        return jpaRepository.findById(searchId).map(this::toDomain);
    }

    @Override
    public long countBySearch(Search search) {
        return jpaRepository.countBySearchFields(
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                serializeAges(search.ages())
        );
    }

    private SearchEntity toEntity(Search search) {
        return new SearchEntity(
                search.searchId(),
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages()
        );
    }

    private Search toDomain(SearchEntity entity) {
        return new Search(
                entity.getSearchId(),
                entity.getHotelId(),
                entity.getCheckIn(),
                entity.getCheckOut(),
                entity.getAges()
        );
    }

    private String serializeAges(List<Integer> ages) {
        try {
            return objectMapper.writeValueAsString(ages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize ages", e);
        }
    }
}
