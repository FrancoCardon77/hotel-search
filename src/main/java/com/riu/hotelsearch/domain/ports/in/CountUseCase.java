package com.riu.hotelsearch.domain.ports.in;

import com.riu.hotelsearch.domain.model.SearchCount;

/**
 * Input port for querying how many times a search has been performed.
 */
public interface CountUseCase {

    /**
     * Returns the search details and the number of times an identical search
     * (same hotel, dates, and ages in the same order) has been recorded.
     *
     * @param searchId UUID of the search to look up
     * @return search details and occurrence count
     * @throws com.riu.hotelsearch.domain.exception.SearchNotFoundException if the ID does not exist
     */
    SearchCount count(String searchId);
}
