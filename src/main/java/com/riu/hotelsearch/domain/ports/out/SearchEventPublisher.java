package com.riu.hotelsearch.domain.ports.out;

import com.riu.hotelsearch.domain.model.Search;

/**
 * Output port for publishing search events to the messaging infrastructure.
 */
public interface SearchEventPublisher {

    /**
     * Publishes a search event. The search must already have a non-null ID assigned.
     *
     * @param search search to publish
     */
    void publish(Search search);
}
