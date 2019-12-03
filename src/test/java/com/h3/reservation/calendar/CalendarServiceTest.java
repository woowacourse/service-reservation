package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CalendarServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CalendarServiceTest.class);

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private Calendar calendar;

    @Mock
    private CalendarId calendarId;

    @Mock
    private Calendar.Events events;

    @Mock
    private Calendar.Events.List list;

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        String summary = "회의실4/스터디/버디";
        String location = "잠실 본동";
        String description = "스터디할거에여";

        String calendarId = "example@group.calendar.google.com";

        Events eventsInCalendar = new Events();
        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.setTimeMin(any())).thenReturn(list);
        when(list.setTimeMax(any())).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        List<Event> fetchedSchedule = calendarService.findReservation(ReservationDateTime.from("2019-12-01"), CalendarId.from(calendarId));

        assertThat(fetchedSchedule.get(0)).isEqualTo(event);
    }
}