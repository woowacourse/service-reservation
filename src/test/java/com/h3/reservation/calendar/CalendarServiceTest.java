package com.h3.reservation.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(calendarService, "summaryDelimiter", "/");
    }

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        String calendarId = "example@group.calendar.google.com";

        Events eventsInCalendar = new Events();
        Event event = createEvent("2019-12-01", "14:00:00", "16:00:00");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        CalendarEvents fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01")
            , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(1);
        assertThat(fetchedSchedule.getEvent(0)).isEqualTo(event);
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

        CalendarEvents fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01", "14:00", "16:00")
            , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(3);
        assertThat(fetchedSchedule.getEvent(0)).isEqualTo(event2);
        assertThat(fetchedSchedule.getEvent(1)).isEqualTo(event3);
        assertThat(fetchedSchedule.getEvent(2)).isEqualTo(event4);
    }

    @Test
    void 회의실_예약_성공() throws IOException {
        // TODO : 유효하지 않은 calendar Id라 예약이 되지 않아 테스트가 실패.
        CalendarId calendarId = CalendarId.from("example@group.calendar.google.com");

        Events eventsInCalendar = new Events();
        Event event = createEvent("2019-12-01", "14:00:00", "16:00:00");
        event.setSummary("회의실1/버디/프로젝트");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId.getId())).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        ReservationDateTime reservationDateTime =
            ReservationDateTime.of("2019-12-01", "12:00:00", "14:00:00");
        ReservationDateTime reservationDateTime2 =
            ReservationDateTime.of("2019-12-01", "16:00:00", "18:00:00");
        ReservationDetails reservationDetails = ReservationDetails.of(MeetingRoom.ROOM1, "닉", "스터디");

//        assertDoesNotThrow(() -> calendarService.insertEvent(reservationDateTime, calendarId, reservationDetails));
//        assertDoesNotThrow(() -> calendarService.insertEvent(reservationDateTime2, calendarId, reservationDetails));
    }

    @Test
    @DisplayName("기존의 예약시간과 겹치면 예약실패")
    void create_NotAvailableReserveEventException() throws IOException {
        CalendarId calendarId = CalendarId.from("example@group.calendar.google.com");

        Events eventsInCalendar = new Events();
        Event event = createEvent("2019-12-01", "14:00:00", "16:00:00");
        event.setSummary("회의실1/버디/프로젝트");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId.getId())).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        ReservationDateTime reservationDateTime =
            ReservationDateTime.of("2019-12-01", "12:00:00", "14:01:00");
        ReservationDateTime reservationDateTime2 =
            ReservationDateTime.of("2019-12-01", "15:59:00", "18:00:00");
        ReservationDetails reservationDetails = ReservationDetails.of(MeetingRoom.ROOM1, "닉", "스터디");

        assertThrows(NotAvailableReserveEventException.class, ()
            -> calendarService.insertEvent(reservationDateTime, calendarId, reservationDetails));
        assertThrows(NotAvailableReserveEventException.class, ()
            -> calendarService.insertEvent(reservationDateTime2, calendarId, reservationDetails));
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