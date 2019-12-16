package com.h3.reservation.calendar;

public class NotFoundEventById extends RuntimeException {
    public NotFoundEventById() {
        super("Id에 해당하는 예약이 없습니다.");
    }
}
