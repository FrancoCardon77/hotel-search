package com.riu.hotelsearch.domain.ports.in;

import com.riu.hotelsearch.domain.model.Search;

/**
 * Input port for registering a hotel availability search.
 */
public interface SearchUseCase {

    /**
     * Assigns a unique ID to the search, publishes it to the event bus,
     * and returns the generated identifier.
     *
     * @param search search payload without an ID
     * @return UUID assigned to this search
     */
    String search(Search search);
}
