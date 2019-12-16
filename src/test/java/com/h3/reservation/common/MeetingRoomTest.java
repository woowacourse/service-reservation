package com.h3.reservation.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}