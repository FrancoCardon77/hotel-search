package com.riu.hotelsearch.infrastructure.rest;

import com.riu.hotelsearch.domain.model.Search;
import com.riu.hotelsearch.domain.model.SearchCount;
import com.riu.hotelsearch.infrastructure.rest.dto.CountResponse;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchDetail;
import com.riu.hotelsearch.infrastructure.rest.dto.SearchRequest;
import org.springframework.stereotype.Component;

@Component
public class SearchMapper {

    public Search toDomain(SearchRequest request) {
        return new Search(null, request.hotelId(), request.checkIn(), request.checkOut(), request.ages());
    }

    public CountResponse toCountResponse(SearchCount searchCount) {
        SearchDetail detail = new SearchDetail(
                searchCount.search().hotelId(),
                searchCount.search().checkIn(),
                searchCount.search().checkOut(),
                searchCount.search().ages()
        );
        return new CountResponse(searchCount.searchId(), detail, searchCount.count());
    }
}
