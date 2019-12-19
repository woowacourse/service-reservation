package com.h3.reservation.calendar.exception;

public class UpdatingEventFailedException extends RuntimeException {
    public UpdatingEventFailedException(final Throwable e) {
        super(e);
    }
}
