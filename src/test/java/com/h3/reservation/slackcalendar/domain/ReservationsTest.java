package com.h3.reservation.slackcalendar.domain;

import com.h3.reservation.common.MeetingRoom;
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
        reservationList.add(Reservation.of("1", MeetingRoom.ROOM1, "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        reservationList.add(Reservation.of("2", MeetingRoom.ROOM2, "코니", "회의", "2019-12-10", "15:00", "16:00"));
        reservationList.add(Reservation.of("3", MeetingRoom.ROOM3, "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        Reservations reservations = Reservations.of(reservationList);
        assertEquals(reservations, Reservations.of(reservationList));
    }

    @Test
    void isEventEmpty() {
        Reservations reservations = Reservations.of(Arrays.asList());
        assertTrue(reservations.isEventEmpty());

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(Reservation.of("1", MeetingRoom.ROOM1, "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        reservationList.add(Reservation.of("2", MeetingRoom.ROOM2, "코니", "회의", "2019-12-10", "15:00", "16:00"));
        reservationList.add(Reservation.of("3", MeetingRoom.ROOM3, "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        reservations = Reservations.of(reservationList);
        assertFalse(reservations.isEventEmpty());
    }

    @Test
    void generateAvailableMeetingRooms() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(Reservation.of("1", MeetingRoom.ROOM1, "희봉", "프로젝트", "2019-12-10", "13:00", "14:00"));
        reservationList.add(Reservation.of("2", MeetingRoom.ROOM3, "코니", "회의", "2019-12-10", "15:00", "16:00"));
        reservationList.add(Reservation.of("3", MeetingRoom.ROOM3, "도넛", "굴러간다", "2019-12-10", "14:00", "14:30"));

        Reservations reservations = Reservations.of(reservationList);
        List<MeetingRoom> meetingRooms = reservations.generateAvailableMeetingRooms();
        assertEquals(meetingRooms, Arrays.asList(MeetingRoom.ROOM2, MeetingRoom.ROOM4, MeetingRoom.ROOM5));
    }
}