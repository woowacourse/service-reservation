package com.h3.reservation.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private Calendar calendar;

    @Mock
    private Calendar.Events events;

    @Mock
    private Calendar.Events.List list;

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        String calendarId = "example@group.calendar.google.com";

        Events eventsInCalendar = new Events();
        Event event = createEvent("2019-12-01", "14:00:00", "16:00:00");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        List<Event> fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01")
            , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(1);
        assertThat(fetchedSchedule.get(0)).isEqualTo(event);
    }

    @Test
    void 특정_시각에_대한_이벤트_조회() throws IOException {
        String calendarId = "example@group.calendar.google.com";

        Events eventsInCalendar = new Events();
        Event event1 = createEvent("2019-12-01", "13:00:00", "14:00:00");
        Event event2 = createEvent("2019-12-01", "13:00:00", "14:01:00");
        Event event3 = createEvent("2019-12-01", "14:00:00", "16:00:00");
        Event event4 = createEvent("2019-12-01", "15:59:00", "17:00:00");
        Event event5 = createEvent("2019-12-01", "16:00:00", "17:00:00");
        eventsInCalendar.setItems(Arrays.asList(event1, event2, event3, event4, event5));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        List<Event> fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01", "14:00", "16:00")
            , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(3);
        assertThat(fetchedSchedule.get(0)).isEqualTo(event2);
        assertThat(fetchedSchedule.get(1)).isEqualTo(event3);
        assertThat(fetchedSchedule.get(2)).isEqualTo(event4);
    }

    private Event createEvent(String date, String startTime, String endTime) {
        return new Event()
            .setStart(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, startTime))))
            .setEnd(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, endTime))));
    }

    private String generateDateTime(String date, String time) {
        return date + "T" + time + "+09:00";
    }
}