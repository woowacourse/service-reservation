package com.h3.reservation.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.h3.reservation.common.MeetingRoom.*;
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
        assertEquals(room, ROOM1);
    }

    @Test
    void removeAll() {
        List<MeetingRoom> rooms = Arrays.asList(ROOM3, ROOM4, PAIR1, PAIR2, PAIR3, PAIR4);
        assertEquals(MeetingRoom.removeAll(rooms), Arrays.asList(ROOM1, ROOM2, ROOM5, PAIR5));
    }

    @Test
    void removeAll_exception() {
        List<MeetingRoom> allRooms = new ArrayList<>(Arrays.asList(MeetingRoom.values()));
        allRooms.remove(NONE);
        assertThrows(NotFoundAvailableMeetingRoomException.class, () -> MeetingRoom.removeAll(allRooms));
    }
}
