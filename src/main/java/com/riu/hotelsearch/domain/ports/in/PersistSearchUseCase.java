package com.riu.hotelsearch.domain.ports.in;

import com.riu.hotelsearch.domain.model.Search;

public interface PersistSearchUseCase {

    void persist(Search search);
}
