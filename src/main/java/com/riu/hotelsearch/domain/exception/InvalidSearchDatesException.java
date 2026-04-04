package com.riu.hotelsearch.domain.exception;

public class InvalidSearchDatesException extends RuntimeException {

    public InvalidSearchDatesException() {
        super("checkIn must be before checkOut");
    }
}
