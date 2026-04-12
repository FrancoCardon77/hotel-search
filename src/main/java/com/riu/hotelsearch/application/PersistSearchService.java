package com.riu.hotelsearch.application;

import com.riu.hotelsearch.application.ports.PersistSearchUseCase;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;

public class PersistSearchService implements PersistSearchUseCase {

    private final SearchRepository repository;

    public PersistSearchService(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void persist(Search search) {
        repository.save(search);
    }
}
