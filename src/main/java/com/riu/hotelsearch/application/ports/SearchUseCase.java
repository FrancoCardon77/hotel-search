package com.riu.hotelsearch.application.ports;

import com.riu.hotelsearch.domain.model.Search;

public interface SearchUseCase {

    String search(Search search);
}
