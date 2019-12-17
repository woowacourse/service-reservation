package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    @Value("${calendar.summary.delimiter:/}")
    private String summaryDelimiter;

    private Calendar calendar;

    public CalendarService(final Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarEvents findReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId) {
        try {
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

    public Event insertEvent(final ReservationDateTime fetchingDate, ReservationDetails reservationDetails, final CalendarId calendarId) throws IOException {
        checkAvailableReservation(fetchingDate, reservationDetails.getMeetingRoom(), calendarId);

        EventDateTime startTime = fetchingDate.toEventDateTime(fetchingDate.getStartDateTime());
        EventDateTime endTime = fetchingDate.toEventDateTime(fetchingDate.getEndDateTime());

        Event event = createEvent(reservationDetails, startTime, endTime);

        return calendar.events()
                .insert(calendarId.getId(), event)
                .execute();
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

    public void deleteEvent(final Event event, final CalendarId calendarId) throws IOException {
        checkExistenceOfEvent(event, calendarId);

        calendar.events()
                .delete(calendarId.getId(), event.getId())
                .execute();
    }

    private void checkExistenceOfEvent(final Event event, final CalendarId calendarId) throws IOException {
        Events results = fetchEventsByCalendarId(calendarId);
        List<Event> items = results.getItems();

        if (notExistsEventInItems(event, items)) {
            throw new EventNotFoundException();
        }
    }

    private boolean notExistsEventInItems(final Event event, final List<Event> items) {
        return items.stream()
                .noneMatch(e -> e.getId().equals(event.getId()));
    }
}
