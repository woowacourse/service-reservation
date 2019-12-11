package com.h3.reservation.slackcalendar.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
class ReservationsTest {
    @Test
    void constructor() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(Reservation.of("회의실1", "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        reservationList.add(Reservation.of("회의실2", "코니", "회의", "2019-12-10", "15:00", "16:00"));
        reservationList.add(Reservation.of("회의실3", "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        Reservations reservations = Reservations.of(reservationList);
        assertEquals(reservations, Reservations.of(reservationList));
    }

    @Test
    void isEventEmpty() {
        Reservations reservations = Reservations.of(Arrays.asList());
        assertTrue(reservations.isEventEmpty());

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(Reservation.of("회의실1", "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        reservationList.add(Reservation.of("회의실2", "코니", "회의", "2019-12-10", "15:00", "16:00"));
        reservationList.add(Reservation.of("회의실3", "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        reservations = Reservations.of(reservationList);
        assertFalse(reservations.isEventEmpty());
    }
}