package com.riu.hotelsearch.application;

import com.riu.hotelsearch.application.ports.CountUseCase;
import com.riu.hotelsearch.domain.exception.SearchNotFoundException;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;

public class CountService implements CountUseCase {

    private final SearchRepository repository;

    public CountService(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchCount count(String searchId) {
        Search search = repository.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));
        long count = repository.countBySearch(search);
        return new SearchCount(searchId, search, count);
    }
}
