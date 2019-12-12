package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.domain.ReservationDetails;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import com.h3.reservation.common.MeetingRoom;
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
            Calendar.Events.List eventList = restrictEventsWithFetchingDate(calendarId);
            Events results = eventList.execute();

            List<Event> events = results.getItems().stream()
                    .filter(item -> fetchingDate.isStartTimeEarlierThan(item.getEnd().getDateTime()))
                    .filter(item -> !fetchingDate.isEndTimeEarlierThanOrEqualTo(item.getStart().getDateTime()))
                    .collect(Collectors.toList());

            return new CalendarEvents(events);
        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private Calendar.Events.List restrictEventsWithFetchingDate(final CalendarId calendarId) throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();

        return eventsInCalendar.list(calendarId.getId());
    }

    public Event insertEvent(final ReservationDateTime fetchingDate, final CalendarId calendarId, ReservationDetails reservationDetails) throws IOException {
        checkValidReservation(fetchingDate, calendarId, reservationDetails.getMeetingRoom());

        EventDateTime startTime = fetchingDate.toEventDateTime(fetchingDate.getStartDateTime());
        EventDateTime endTime = fetchingDate.toEventDateTime(fetchingDate.getEndDateTime());

        Event event = createEvent(reservationDetails, startTime, endTime);

        calendar.events().insert(calendarId.getId(), event);
        return event;
    }

    private void checkValidReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId, MeetingRoom room) {
        CalendarEvents eventsByTime = findReservation(fetchingDate, calendarId);
        if (isReservedMeetingRoom(room, eventsByTime)) {
            throw new NotAvailableReserveEventException("이미 예약된 방이 있습니다!");
        }
    }

    private boolean isReservedMeetingRoom(MeetingRoom room, CalendarEvents eventsByTime) {
        return eventsByTime.findMeetingRooms()
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
}
