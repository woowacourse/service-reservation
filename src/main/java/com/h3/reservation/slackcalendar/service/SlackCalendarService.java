package com.h3.reservation.slackcalendar.service;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
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
        CalendarEvents reservation = calendarService.findEvents(ReservationDateTime.of(dateTime.getFormattedDate(), dateTime.getFormattedStartTime(), dateTime.getFormattedEndTime())
            , CalendarId.from(calendarId));

        return Reservations.of(
            reservation.getEvents().stream()
                .filter(event -> ReservationConverter.isFormatted(event.getSummary(), summaryDelimiter))
                .map(event -> ReservationConverter.toReservation(event, summaryDelimiter))
                .sorted(Comparator.comparing(Reservation::getFormattedStartTime))
                .collect(Collectors.toList())
        );
    }

    public Reservations retrieve(String date, String booker) {
        CalendarEvents reservations = calendarService.findEvents(ReservationDateTime.of(date), CalendarId.from(calendarId));
        return Reservations.of(
            reservations.getEvents().stream()
                .filter(event -> ReservationConverter.isFormatted(event.getSummary(), summaryDelimiter))
                .map(event -> ReservationConverter.toReservation(event, summaryDelimiter))
                .filter(reservation -> reservation.isSameBooker(booker))
                .sorted(Comparator.comparing(Reservation::getFormattedStartTime))
                .collect(Collectors.toList())
        );
    }

    public Reservation retrieveById(String id) {
        // TODO : Event event = calendarService.findReservation(id, calendarId)
        ReservationDetails details = ReservationDetails.of(MeetingRoom.ROOM1, "희봉", "그냥 넣었찌");
        DateTime dateTime = DateTime.of("2019-12-17", "17:00", "18:00");
        return Reservation.of(id, details, dateTime);
    }

    public Reservation reserve(ReservationDetails details, DateTime dateTime) throws IOException {
        Event event = calendarService.insertEvent(ReservationDateTime.of(dateTime.getFormattedDate(), dateTime.getFormattedStartTime(), dateTime.getFormattedEndTime())
            , details, CalendarId.from(calendarId));
        return ReservationConverter.toReservation(event, summaryDelimiter);
    }

    public Reservation change(Reservation preReservation) {
        // TODO : Event event = calendarService.change(id ,ReservationDateTime, detail, calendarId)
        return preReservation;
    }

    public void cancel(Reservation reservation) {
        // TODO : calendar.cancel(reservation.getId(), calendarId)

    }
}
