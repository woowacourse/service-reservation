package com.h3.reservation.calendar.domain;

public class CalendarId {

    private String id;

    private CalendarId(final String id) {
        this.id = id;
    }

    public static CalendarId from(final String id) {
        return new CalendarId(id);
    }

    public String getId() {
        return id;
    }
}