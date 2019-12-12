package com.h3.reservation.calendar.domain;

import com.h3.reservation.common.MeetingRoom;

public class ReservationDetails {

    private final MeetingRoom meetingRoom;
    private final String booker;
    private final String description;

    private ReservationDetails(final MeetingRoom meetingRoom, final String booker, final String description) {
        this.meetingRoom = meetingRoom;
        this.booker = booker;
        this.description = description;
    }

    public static ReservationDetails of(final MeetingRoom meetingRoom, final String booker, final String description) {
        return new ReservationDetails(meetingRoom, booker, description);
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public String getBooker() {
        return booker;
    }

    public String getDescription() {
        return description;
    }
}
