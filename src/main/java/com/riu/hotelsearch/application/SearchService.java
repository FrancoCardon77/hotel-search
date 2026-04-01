package com.riu.hotelsearch.application;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.in.SearchUseCase;
import com.riu.hotelsearch.domain.ports.out.SearchEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Generates a unique ID for the search, attaches it to the domain object,
 * and delegates publishing to the event bus.
 *
 * <p>The ID is assigned here rather than in the controller or the database
 * so the caller receives it synchronously while persistence happens
 * asynchronously through the Kafka consumer.</p>
 */
@Service
@RequiredArgsConstructor
public class SearchService implements SearchUseCase {

    private final SearchEventPublisher publisher;

    @Override
    public String search(Search search) {
        String searchId = UUID.randomUUID().toString();
        Search searchWithId = new Search(
                searchId,
                search.hotelId(),
                search.checkIn(),
                search.checkOut(),
                search.ages()
        );
        publisher.publish(searchWithId);
        return searchId;
    }
}
