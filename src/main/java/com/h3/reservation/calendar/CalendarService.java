package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.exception.DeletingEventFailedException;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private static final Logger log = LoggerFactory.getLogger(CalendarService.class);
    protected static final String CANCELLED_EVENT_STATUS = "cancelled";

    @Value("${calendar.summary.delimiter:/}")
    private String summaryDelimiter;

    private Calendar calendar;

    public CalendarService(final Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarEvents findReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId) {
        try {
            log.debug("find - fetching date : {}", fetchingDate);
            Events results = fetchEventsByCalendarId(calendarId);

            List<Event> events = results.getItems().stream()
                    .filter(item -> fetchingDate.isStartTimeEarlierThan(item.getEnd().getDateTime()))
                    .filter(item -> !fetchingDate.isEndTimeEarlierThanOrEqualTo(item.getStart().getDateTime()))
                    .collect(Collectors.toList());

            return new CalendarEvents(events);
        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private Events fetchEventsByCalendarId(final CalendarId calendarId) throws IOException {
        Calendar.Events.List eventList = findListByCalendarId(calendarId);
        return eventList.execute();
    }

    private Calendar.Events.List findListByCalendarId(final CalendarId calendarId) throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();

        return eventsInCalendar.list(calendarId.getId());
    }

    public Optional<Event> findEventById(final String eventId, final CalendarId calendarId) {
        try {
            log.debug("find - fetching event : eventId={}", eventId);
            Event fetchedEvent = calendar.events()
                    .get(calendarId.getId(), eventId)
                    .execute();

            return isCancelled(eventId, fetchedEvent) ? Optional.empty() : Optional.of(fetchedEvent);
        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private boolean isCancelled(final String eventId, final Event fetchedEvent) {
        String eventStatus = fetchedEvent.getStatus();
        if (CANCELLED_EVENT_STATUS.equals(eventStatus)) {
            log.debug("event was cancelled : eventId={}", eventId);
            return true;
        }
        return false;
    }

    public Event insertEvent(final ReservationDateTime fetchingDate, ReservationDetails reservationDetails, final CalendarId calendarId) throws IOException {
        log.debug("insert - fetching date : {}, details : {}", fetchingDate, reservationDetails);
        checkAvailableReservation(fetchingDate, reservationDetails.getMeetingRoom(), calendarId);

        EventDateTime startTime = fetchingDate.toEventDateTime(fetchingDate.getStartDateTime());
        EventDateTime endTime = fetchingDate.toEventDateTime(fetchingDate.getEndDateTime());

        Event event = createEvent(reservationDetails, startTime, endTime);

        Event insertedEvent = calendar.events()
                .insert(calendarId.getId(), event)
                .execute();
        log.debug("inserted event : {}", insertedEvent.getId());
        return insertedEvent;
    }

    private void checkAvailableReservation(final ReservationDateTime fetchingDate, MeetingRoom room, final CalendarId calendarId) {
        CalendarEvents eventsByTime = findReservation(fetchingDate, calendarId);
        if (isReservedMeetingRoom(room, eventsByTime)) {
            throw new NotAvailableReserveEventException("이미 예약된 방이 있습니다!");
        }
    }

    private boolean isReservedMeetingRoom(MeetingRoom room, CalendarEvents eventsByTime) {
        return eventsByTime.findMeetingRooms(summaryDelimiter)
                .stream()
                .anyMatch(meetingRoom -> meetingRoom.equals(room));
    }

    private Event createEvent(final ReservationDetails reservationDetails, final EventDateTime startTime, final EventDateTime endTime) {
        MeetingRoom meetingRoom = reservationDetails.getMeetingRoom();
        String booker = reservationDetails.getBooker();
        String description = reservationDetails.getDescription();

        return new Event()
                .setStart(startTime)
                .setEnd(endTime)
                .setSummary(meetingRoom.getName() + summaryDelimiter + booker + summaryDelimiter + description);
    }

    public void deleteEvent(final String eventId, final CalendarId calendarId) {
        try {
            log.debug("cancel - eventId : {}", eventId);

            calendar.events()
                    .delete(calendarId.getId(), eventId)
                    .execute();
        } catch (IOException e) {
            throw new DeletingEventFailedException(e);
        }
    }
}
