package com.h3.reservation.slackcalendar.converter.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.h3.reservation.slackcalendar.converter.ReservationConverter;
import com.h3.reservation.slackcalendar.domain.Reservation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
class ReservationConverterTest {

    @Test
    void toReservation() {
        Event event = createEvent("회의실1/희봉/프로젝트", "2019-12-10T10:30:00.000+09:00", "2019-12-10T12:00:00.000+09:00");
        Reservation slackCalendarReservation = Reservation.of("회의실1", "희봉", "프로젝트", "2019-12-10", "10:30", "12:00");
        assertEquals(ReservationConverter.toReservation(event, "/"), slackCalendarReservation);
    }

    private Event createEvent(String summary, String startTime, String endTime) {
        return new Event()
                .setSummary(summary)
                .setStart(new EventDateTime().setDateTime(new DateTime(startTime)))
                .setEnd(new EventDateTime().setDateTime(DateTime.parseRfc3339(endTime)));
    }
}