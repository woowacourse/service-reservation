package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private Calendar calendar;

    public CalendarService(final Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Event> findReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId) {
        try {
            Calendar.Events.List eventList = restrictEventsWithFetchingDate(calendarId);
            Events results = eventList.execute();

            return results.getItems().stream()
                    .filter(item -> fetchingDate.isStartTimeEarlierThanOrEqualTo(item.getEnd().getDateTime()))
                    .filter(item -> !fetchingDate.isEndTimeEarlierThanOrEqualTo(item.getStart().getDateTime()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private Calendar.Events.List restrictEventsWithFetchingDate(final CalendarId calendarId) throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();

        return eventsInCalendar.list(calendarId.getId());
    }
}
