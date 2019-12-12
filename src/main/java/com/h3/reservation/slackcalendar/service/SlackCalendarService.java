package com.h3.reservation.slackcalendar.service;

import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.slackcalendar.converter.ReservationConverter;
import com.h3.reservation.slackcalendar.domain.DateTime;
import com.h3.reservation.slackcalendar.domain.Reservations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
                        .map(event -> ReservationConverter.toReservation(event, summaryDelimiter))
                        .collect(Collectors.toList())
        );
    }
}
