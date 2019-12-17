package com.h3.reservation.calendar;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Id에 해당하는 예약이 없습니다.");
    }
}
