package com.h3.reservation.slackcalendar.exception;

public class InvalidEventException extends RuntimeException {
    public InvalidEventException() {
        super("잘못된 예약입니다.");
    }
}
