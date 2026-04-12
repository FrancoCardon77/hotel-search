package com.riu.hotelsearch.infrastructure.persistence;

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
                computeAgesHash(search.ages())
        );
    }

    private SearchEntity toEntity(Search search) {
        return new SearchEntity(
                search.searchId(),
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages(),
                computeAgesHash(search.ages())
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

    private Integer computeAgesHash(List<Integer> ages) {
        return ages != null ? ages.hashCode() : 0;
    }
}
