package com.h3.reservation.slackcalendar.domain;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
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
        String id = "1";
        MeetingRoom room = MeetingRoom.ROOM1;
        String booker = "희봉";
        String purpose = "프로젝트 회의";

        LocalDate date = LocalDate.of(2019, 12, 10);
        LocalTime startTime = LocalTime.of(13, 30);
        LocalTime endTime = LocalTime.of(15, 00);

        Reservation reservation = Reservation.of(id, room, booker, purpose, date, startTime, endTime);
        assertEquals(reservation, Reservation.of(id, room, booker, purpose, date, startTime, endTime));

        ReservationDetails details = ReservationDetails.of(room, booker, purpose);
        DateTime eventTime = DateTime.of(date, startTime, endTime);
        assertEquals(reservation, Reservation.of(id, details, eventTime));
    }

    @Test
    void constructor_all_string_without_room() {
        String id = "1";
        MeetingRoom room = MeetingRoom.ROOM1;
        String booker = "희봉";
        String purpose = "프로젝트 회의";

        String date = "2019-12-10";
        String startTime = "13:00";
        String endTime = "15:00";

        Reservation reservation = Reservation.of(id, room, booker, purpose, date, startTime, endTime);
        assertEquals(reservation, Reservation.of(id, room, booker, purpose, date, startTime, endTime));
    }
}