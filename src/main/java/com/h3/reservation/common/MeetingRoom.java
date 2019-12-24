package com.h3.reservation.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MeetingRoom {
    ROOM1("회의실1"),
    ROOM2("회의실2"),
    ROOM3("회의실3"),
    ROOM4("회의실4"),
    ROOM5("회의실5"),
    NONE("");

    private String name;

    MeetingRoom(String name) {
        this.name = name;
    }

    public static MeetingRoom findByName(String name) {
        return Arrays.stream(values())
            .filter(room -> room.name.equals(removeBlank(name)))
            .findFirst()
            .orElse(NONE);
    }

    private static String removeBlank(final String name) {
        return name.replace(" ", "");
    }

    public static List<MeetingRoom> removeAll(List<MeetingRoom> meetingRooms) {
        List<MeetingRoom> allRooms = new ArrayList<>(Arrays.asList(values()));
        allRooms.removeAll(meetingRooms);
        validateOnlyWithNone(allRooms);
        return allRooms;
    }

    private static void validateOnlyWithNone(List<MeetingRoom> rooms) {
        rooms.remove(MeetingRoom.NONE);
        if (rooms.size() == 0) {
            throw new NotFoundAvailableMeetingRoomException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MeetingRoom{" +
            "name='" + name + '\'' +
            '}';
    }
}
