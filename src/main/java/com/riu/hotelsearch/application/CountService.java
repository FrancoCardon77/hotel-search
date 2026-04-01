package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.exception.SearchNotFoundException;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.domain.ports.in.CountUseCase;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Resolves the search by ID and counts how many identical searches exist in the database.
 *
 * <p>The count query uses the full search fields (hotel, dates, ages in order)
 * as the matching criteria, not just the ID.</p>
 */
@Service
@RequiredArgsConstructor
public class CountService implements CountUseCase {

    private final SearchRepository repository;

    @Override
    public SearchCount count(String searchId) {
        Search search = repository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));
        long count = repository.countBySearch(search);
        return new SearchCount(searchId, search, count);
    }
}
