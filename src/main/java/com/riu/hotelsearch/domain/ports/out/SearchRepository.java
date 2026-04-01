package com.riu.hotelsearch.domain.ports.out;

import com.riu.hotelsearch.domain.model.Search;
import java.util.Optional;

/**
 * Output port for persisting and querying searches.
 */
public interface SearchRepository {

    /**
     * Persists a search. The search must have a non-null ID.
     *
     * @param search search to save
     */
    void save(Search search);

    /**
     * Finds a search by its unique identifier.
     *
     * @param searchId UUID of the search
     * @return the search, or empty if not found
     */
    Optional<Search> findById(String searchId);

    /**
     * Counts how many times an identical search has been recorded.
     * Two searches are identical when hotel, check-in, check-out and ages
     * (including order) all match.
     *
     * @param search reference search to compare against
     * @return number of matching records
     */
    long countBySearch(Search search);
}
