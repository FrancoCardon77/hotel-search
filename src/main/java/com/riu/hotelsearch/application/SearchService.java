package com.riu.hotelsearch.application;

import com.riu.hotelsearch.application.ports.SearchUseCase;
import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.ports.out.SearchEventPublisher;

import java.util.UUID;

public class SearchService implements SearchUseCase {

    private final SearchEventPublisher publisher;

    public SearchService(SearchEventPublisher publisher) {
        this.publisher = publisher;
    }

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
