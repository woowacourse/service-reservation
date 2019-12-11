package com.h3.reservation.slackcalendar.service;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.slackcalendar.converter.EventConverter;
import com.h3.reservation.slackcalendar.domain.EventDateTime;
import com.h3.reservation.slackcalendar.domain.Events;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
@Service
public class SlackCalendarService {
    private final CalendarService calendarService;

    @Value("${calendar.id}")
    private String calendarId;

    public SlackCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public Events retrieve(EventDateTime dateTime) {
        List<Event> events = calendarService.findReservation(ReservationDateTime.of(dateTime.getFormattedDate(), dateTime.getFormattedStartTime(), dateTime.getFormattedEndTime())
            , CalendarId.from(calendarId));

        return Events.of(
            events.stream()
                .map(EventConverter::toSlackCalendarEvent)
                .collect(Collectors.toList())
        );
    }
}
