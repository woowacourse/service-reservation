package com.h3.reservation.calendar.domain;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.common.MeetingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarEventsTest {
    private List<Event> events;

    @BeforeEach
    void setUp() {
        Event event1 = new Event().setSummary("회의실 1 / 버디 / 스터디");
        Event event2 = new Event().setSummary("회의실 2 / 닉 / 프로젝트");
        Event event3 = new Event().setSummary("회의실 3 / 코니 / 미션");

        events = Arrays.asList(event1, event2, event3);
    }

    @Test
    void findSummaries() {
        CalendarEvents calendarEvents = new CalendarEvents(events);

        List<String> summaries = calendarEvents.findSummaries();

        assertThat(summaries).isEqualTo(Arrays.asList("회의실 1 / 버디 / 스터디", "회의실 2 / 닉 / 프로젝트", "회의실 3 / 코니 / 미션"));
    }

    @Test
    void findMeetingRooms() {
        CalendarEvents calendarEvents = new CalendarEvents(events);

        List<MeetingRoom> meetingRooms = calendarEvents.findMeetingRooms("/");

        assertThat(meetingRooms).isEqualTo(Arrays.asList(MeetingRoom.ROOM1, MeetingRoom.ROOM2, MeetingRoom.ROOM3));
    }

    @Test
    void size() {
        assertThat(events.size()).isEqualTo(3);
    }

    @Test
    void getEvent() {
        CalendarEvents calendarEvents = new CalendarEvents(events);

        assertThat(calendarEvents.getEvent(0)).isEqualTo(events.get(0));
        assertThat(calendarEvents.getEvent(1)).isEqualTo(events.get(1));
        assertThat(calendarEvents.getEvent(2)).isEqualTo(events.get(2));
    }
}