package com.h3.reservation.common;

import java.util.Arrays;

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
                .filter(room -> room.name.equals(name))
                .findFirst()
                .orElse(NONE);
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
