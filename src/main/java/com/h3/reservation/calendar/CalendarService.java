package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CalendarService {

    @Value("${calendar.id}")
    private String calenderId;

    private Calendar calendar;

    public CalendarService(final Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Event> findReservation(String fetchingDate) {
        try {
            Calendar.Events.List eventList = restrictEventsWithFetchingDate(fetchingDate);
            Events results = eventList.execute();

            return results.getItems();
        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private Calendar.Events.List restrictEventsWithFetchingDate(final String fetchingDate) throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();

        ReservationDateTime reservationDateTime = ReservationDateTime.from(fetchingDate);

        return eventsInCalendar.list(calenderId)
                .setTimeMin(reservationDateTime.getStartDateTime())
                .setTimeMax(reservationDateTime.getEndDateTime());
    }
}
