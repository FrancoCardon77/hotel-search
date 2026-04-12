package com.riu.hotelsearch.application.ports;

import com.riu.hotelsearch.domain.model.Search;

public interface PersistSearchUseCase {

    void persist(Search search);
}
