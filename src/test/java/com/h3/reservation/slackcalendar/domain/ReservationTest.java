package com.h3.reservation.slackcalendar.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
class ReservationTest {
    @Test
    void constructor() {
        String room = "회의실1";
        String booker = "희봉";
        String purpose = "프로젝트 회의";

        LocalDate date = LocalDate.of(2019, 12, 10);
        LocalTime startTime = LocalTime.of(13, 30);
        LocalTime endTime = LocalTime.of(15, 00);

        Reservation reservation = Reservation.of(room, booker, purpose, date, startTime, endTime);
        assertEquals(reservation, Reservation.of(room, booker, purpose, date, startTime, endTime));

        Component component = Component.of(room, booker, purpose);
        DateTime eventTime = DateTime.of(date, startTime, endTime);
        assertEquals(reservation, Reservation.of(component, eventTime));
    }

    @Test
    void constructor_all_string() {
        String room = "회의실1";
        String booker = "희봉";
        String purpose = "프로젝트 회의";

        String date = "2019-12-10";
        String startTime = "13:00";
        String endTime = "15:00";

        Reservation reservation = Reservation.of(room, booker, purpose, date, startTime, endTime);
        assertEquals(reservation, Reservation.of(room, booker, purpose, date, startTime, endTime));
    }
}