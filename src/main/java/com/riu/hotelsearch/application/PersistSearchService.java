package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.in.PersistSearchUseCase;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersistSearchService implements PersistSearchUseCase {

    private final SearchRepository repository;

    @Override
    @Transactional
    public void persist(Search search) {
        repository.save(search);
    }
}
