package com.h3.reservation.calendar;

public class NotAvailableReserveEventException extends RuntimeException {
    public NotAvailableReserveEventException(String message) {
        super(message);
    }
}
