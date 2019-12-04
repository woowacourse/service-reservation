package com.h3.reservation.calendar.exception;

public class FetchingEventsFailedException extends RuntimeException {
    public FetchingEventsFailedException(final Throwable cause) {
        super(cause);
    }
}
