package com.h3.reservation.slackcalendar.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
class EventsTest {
    @Test
    void constructor() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(Event.of("회의실1", "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        eventList.add(Event.of("회의실2", "코니", "회의", "2019-12-10", "15:00", "16:00"));
        eventList.add(Event.of("회의실3", "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        Events events = Events.of(eventList);
        assertEquals(events, Events.of(eventList));
    }

    @Test
    void isEventEmpty() {
        Events events = Events.of(Arrays.asList());
        assertTrue(events.isEventEmpty());

        List<Event> eventList = new ArrayList<>();
        eventList.add(Event.of("회의실1", "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        eventList.add(Event.of("회의실2", "코니", "회의", "2019-12-10", "15:00", "16:00"));
        eventList.add(Event.of("회의실3", "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        events = Events.of(eventList);
        assertFalse(events.isEventEmpty());
    }
}