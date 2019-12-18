package com.h3.reservation.calendar.exception;

public class InsertingEventFailedException extends RuntimeException {
    public InsertingEventFailedException(final Throwable e) {
        super(e);
    }
}
