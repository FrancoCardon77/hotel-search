package com.riu.hotelsearch.domain.exception;

public class SearchNotFoundException extends RuntimeException {

    public SearchNotFoundException(String searchId) {
        super(String.format("Search not found: %s", searchId));
    }
}
