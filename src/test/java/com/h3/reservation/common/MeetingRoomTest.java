package com.h3.reservation.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-13
 */
class MeetingRoomTest {
    @Test
    void findByName() {
        MeetingRoom room = MeetingRoom.findByName("회의실1");
        assertEquals(room, MeetingRoom.ROOM1);
    }

    @Test
    void removeAll() {
        List<MeetingRoom> rooms = Arrays.asList(MeetingRoom.ROOM3, MeetingRoom.ROOM4);
        assertEquals(MeetingRoom.removeAll(rooms), Arrays.asList(MeetingRoom.ROOM1, MeetingRoom.ROOM2, MeetingRoom.ROOM5));
    }

    @Test
    void removeAll_exception() {
        List<MeetingRoom> allRooms = new ArrayList<>(Arrays.asList(MeetingRoom.values()));
        allRooms.remove(MeetingRoom.NONE);
        assertThrows(NotFoundAvailableMeetingRoomException.class, () -> MeetingRoom.removeAll(allRooms));
    }
}