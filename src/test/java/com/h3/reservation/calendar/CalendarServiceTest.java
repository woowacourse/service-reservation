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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Mock
    private Calendar.Events.Get get;

    @Mock
    private Calendar.Events.Insert insert;

    @Mock
    private Calendar.Events.Update update;

    @Mock
    private Calendar.Events.Delete delete;

    private String calendarId;
    private List<Event> dummyEvents;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(calendarService, "summaryDelimiter", "/");
        calendarId = "example@group.calendar.google.com";

        Event event1 = createEvent("2019-12-01", "13:00:00", "14:00:00", "event1");
        Event event2 = createEvent("2019-12-01", "13:00:00", "14:01:00", "event2");
        Event event3 = createEvent("2019-12-01", "14:00:00", "16:00:00", "event3");
        Event event4 = createEvent("2019-12-01", "15:59:00", "17:00:00", "event4");
        Event event5 = createEvent("2019-12-01", "16:00:00", "17:00:00", "event5");
        dummyEvents = Arrays.asList(event1, event2, event3, event4, event5);
    }

    private Event createEvent(String date, String startTime, String endTime, String id) {
        return new Event()
                .setStart(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, startTime))))
                .setEnd(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, endTime))))
                .setId(id);
    }

    private String generateDateTime(String date, String time) {
        return date + "T" + time + "+09:00";
    }

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        Events eventsInCalendar = new Events();
        eventsInCalendar.setItems(dummyEvents);

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        CalendarEvents fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01")
                , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(5);
        for (int index = 0; index < fetchedSchedule.size(); index++) {
            assertThat(fetchedSchedule.getEvent(index)).isEqualTo(dummyEvents.get(index));
        }
    }

    @Test
    void 특정_시각에_대한_이벤트_조회() throws IOException {
        Events eventsInCalendar = new Events();
        eventsInCalendar.setItems(dummyEvents.subList(1, 4));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        CalendarEvents fetchedSchedule = calendarService.findReservation(ReservationDateTime.of("2019-12-01", "14:00", "16:00")
                , CalendarId.from(calendarId));

        assertThat(fetchedSchedule.size()).isEqualTo(3);
        assertThat(fetchedSchedule.getEvent(0)).isEqualTo(dummyEvents.get(1));
        assertThat(fetchedSchedule.getEvent(1)).isEqualTo(dummyEvents.get(2));
        assertThat(fetchedSchedule.getEvent(2)).isEqualTo(dummyEvents.get(3));
    }

    @Test
    @DisplayName("id로 Event 조회하기")
    void findEventById() throws IOException {
        Event targetEvent = dummyEvents.get(0);

        when(calendar.events()).thenReturn(events);
        when(events.get(calendarId, targetEvent.getId())).thenReturn(get);
        when(get.execute()).thenReturn(targetEvent);

        assertThat(calendarService.findEventById(targetEvent.getId(), CalendarId.from(calendarId))).isEqualTo(Optional.of(targetEvent));
    }

    @Test
    @DisplayName("id로 이미 삭제된 Event를 조회하면 비어있는 값 반환")
    void findEventById_cancelled() throws IOException {
        Event targetEvent = dummyEvents.get(0);
        targetEvent.setStatus(CalendarService.CANCELLED_EVENT_STATUS);

        when(calendar.events()).thenReturn(events);
        when(events.get(calendarId, targetEvent.getId())).thenReturn(get);
        when(get.execute()).thenReturn(targetEvent);

        assertThat(calendarService.findEventById(targetEvent.getId(), CalendarId.from(calendarId))).isEqualTo(Optional.empty());
    }

    @Test
    void 회의실_예약_성공() throws IOException {
        Events eventsInCalendar = new Events();
        Event event = dummyEvents.get(0);
        event.setSummary("회의실1/버디/프로젝트");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        ReservationDateTime reservationDateTime =
                ReservationDateTime.of("2019-12-01", "14:00:00", "15:00:00");
        ReservationDetails reservationDetails = ReservationDetails.of(MeetingRoom.ROOM1, "닉", "스터디");

        Event insertedEvent = createEvent("2019-12-01", "14:00:00", "15:00:00", "insertedEvent");
        when(events.insert(eq(calendarId), any(Event.class))).thenReturn(insert);
        when(insert.execute()).thenReturn(insertedEvent);

        assertDoesNotThrow(() -> calendarService.insertEvent(reservationDateTime, reservationDetails, CalendarId.from(calendarId)));
        verify(insert, times(1)).execute();
    }

    @Test
    @DisplayName("기존의 예약시간과 겹치면 예약실패")
    void create_NotAvailableReserveEventException() throws IOException {
        Events eventsInCalendar = new Events();
        Event event = dummyEvents.get(2);
        event.setSummary("회의실1/버디/프로젝트");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        ReservationDateTime reservationDateTime =
                ReservationDateTime.of("2019-12-01", "12:00:00", "14:01:00");
        ReservationDateTime reservationDateTime2 =
                ReservationDateTime.of("2019-12-01", "15:59:00", "18:00:00");
        ReservationDetails reservationDetails = ReservationDetails.of(MeetingRoom.ROOM1, "닉", "스터디");

        assertThrows(NotAvailableReserveEventException.class, ()
                -> calendarService.insertEvent(reservationDateTime, reservationDetails, CalendarId.from(calendarId)));
        assertThrows(NotAvailableReserveEventException.class, ()
                -> calendarService.insertEvent(reservationDateTime2, reservationDetails, CalendarId.from(calendarId)));
    }

    @Test
    void updateEvent() throws IOException {
        Events eventsInCalendar = new Events();
        Event event = dummyEvents.get(0);
        event.setSummary("회의실1/버디/프로젝트");
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);

        ReservationDateTime reservationDateTime =
                ReservationDateTime.of("2019-12-01", "14:00:00", "15:00:00");
        ReservationDetails reservationDetails = ReservationDetails.of(MeetingRoom.ROOM1, "닉", "스터디");

        Event updatedEvent = createEvent("2019-12-01", "15:00:00", "16:00:00", "insertedEvent");
        when(events.update(eq(calendarId), eq(event.getId()), any(Event.class))).thenReturn(update);
        when(update.execute()).thenReturn(updatedEvent);

        assertDoesNotThrow(() -> calendarService.updateEvent(event.getId(), reservationDateTime, reservationDetails, CalendarId.from(calendarId)));
        verify(update, times(1)).execute();
    }

    @Test
    void deleteEvent() throws IOException {
        Events eventsInCalendar = new Events();
        Event event1 = dummyEvents.get(1);
        Event event2 = dummyEvents.get(2);
        eventsInCalendar.setItems(Arrays.asList(event1, event2));

        when(calendar.events()).thenReturn(events);
        when(events.list(calendarId)).thenReturn(list);
        when(list.execute()).thenReturn(eventsInCalendar);
        when(events.delete(calendarId, event1.getId())).thenReturn(delete);

        assertDoesNotThrow(() -> calendarService.deleteEvent(event1.getId(), CalendarId.from(calendarId)));
        verify(delete, times(1)).execute();
    }
}