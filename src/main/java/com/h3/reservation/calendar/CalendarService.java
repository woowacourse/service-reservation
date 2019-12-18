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
import com.h3.reservation.calendar.exception.InsertingEventFailedException;
import com.h3.reservation.calendar.exception.UpdatingEventFailedException;
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
            log.debug("find by date : fetching date = {}", fetchingDate);
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
            log.debug("find by id : fetching event id = {}", eventId);
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
            log.debug("event was cancelled : event id = {}", eventId);
            return true;
        }
        return false;
    }

    public Event insertEvent(final ReservationDateTime fetchingDate, ReservationDetails reservationDetails, final CalendarId calendarId) {
        try {
            log.debug("insert : fetching date = {}, details = {}", fetchingDate, reservationDetails);
            checkAvailableReservation(fetchingDate, reservationDetails.getMeetingRoom(), calendarId);

            Event event = createEventWith(fetchingDate, reservationDetails);

            Event insertedEvent = calendar.events()
                    .insert(calendarId.getId(), event)
                    .execute();
            log.debug("inserted event : event id = {}", insertedEvent.getId());
            return insertedEvent;
        } catch (IOException e) {
            throw new InsertingEventFailedException(e);
        }
    }

    private Event createEventWith(final ReservationDateTime fetchingDate, final ReservationDetails reservationDetails) {
        EventDateTime startTime = fetchingDate.createEventDateTimeFromStartDateTime();
        EventDateTime endTime = fetchingDate.createEventDateTimeFromEndDateTime();

        return createEvent(reservationDetails, startTime, endTime);
    }

    private Event createEvent(final ReservationDetails reservationDetails, final EventDateTime startTime, final EventDateTime endTime) {
        String summary = createSummary(reservationDetails);

        return new Event()
                .setStart(startTime)
                .setEnd(endTime)
                .setSummary(summary);
    }

    private String createSummary(final ReservationDetails reservationDetails) {
        MeetingRoom meetingRoom = reservationDetails.getMeetingRoom();
        String booker = reservationDetails.getBooker();
        String description = reservationDetails.getDescription();

        return meetingRoom.getName() + summaryDelimiter + booker + summaryDelimiter + description;
    }

    private void checkAvailableReservation(final ReservationDateTime fetchingDate, MeetingRoom room, final CalendarId calendarId) {
        CalendarEvents eventsByTime = findReservation(fetchingDate, calendarId);
        if (isReservedMeetingRoom(room, eventsByTime)) {
            log.debug("already reservation exists : fetching date = {} ,room = {}", fetchingDate, room);
            throw new NotAvailableReserveEventException("이미 예약된 방이 있습니다!");
        }
    }

    private boolean isReservedMeetingRoom(MeetingRoom room, CalendarEvents eventsByTime) {
        return eventsByTime.findMeetingRooms(summaryDelimiter)
                .stream()
                .anyMatch(meetingRoom -> meetingRoom.equals(room));
    }

    public Event updateEvent(final String eventId, final ReservationDateTime fetchingDate, ReservationDetails reservationDetails, final CalendarId calendarId) {
        try {
            log.debug("update : event id = {}, fetching date = {}, details = {}", eventId, fetchingDate, reservationDetails);
            checkAvailableReservation(fetchingDate, reservationDetails.getMeetingRoom(), calendarId);

            Event newEvent = createEventWith(fetchingDate, reservationDetails);

            Event updatedEvent = calendar.events()
                    .update(calendarId.getId(), eventId, newEvent)
                    .execute();
            log.debug("updated event : event id = {}", updatedEvent.getId());
            return updatedEvent;
        } catch (IOException e) {
            throw new UpdatingEventFailedException(e);
        }
    }

    public void deleteEvent(final String eventId, final CalendarId calendarId) {
        try {
            log.debug("cancel : event id = {}", eventId);

            calendar.events()
                    .delete(calendarId.getId(), eventId)
                    .execute();
        } catch (IOException e) {
            throw new DeletingEventFailedException(e);
        }
    }
}
