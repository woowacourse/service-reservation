package com.h3.reservation.slackcalendar.service;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.slackcalendar.converter.ReservationConverter;
import com.h3.reservation.slackcalendar.domain.DateTime;
import com.h3.reservation.slackcalendar.domain.Reservation;
import com.h3.reservation.slackcalendar.domain.Reservations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
@Service
public class SlackCalendarService {
    private final CalendarService calendarService;

    private final String calendarId = System.getenv("CALENDAR_ID");

    @Value("${calendar.summary.delimiter:/}")
    private String summaryDelimiter;

    public SlackCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public Reservations retrieve(DateTime dateTime) {
        CalendarEvents reservation = calendarService.findReservation(ReservationDateTime.of(dateTime.getFormattedDate(), dateTime.getFormattedStartTime(), dateTime.getFormattedEndTime())
            , CalendarId.from(calendarId));

        return Reservations.of(
            reservation.getEvents().stream()
                .filter(event -> ReservationConverter.isFormatted(event.getSummary(), summaryDelimiter))
                .map(event -> ReservationConverter.toReservation(event, summaryDelimiter))
                .sorted(Comparator.comparing(Reservation::getFormattedStartTime))
                .collect(Collectors.toList())
        );
    }

    public Reservation reserve(Reservation reservation) throws IOException {
        Event event = calendarService.insertEvent(ReservationDateTime.of(reservation.getFormattedDate(), reservation.getFormattedStartTime(), reservation.getFormattedEndTime())
            , CalendarId.from(calendarId), reservation.getDetails());
        return ReservationConverter.toReservation(event, summaryDelimiter);
    }
}
