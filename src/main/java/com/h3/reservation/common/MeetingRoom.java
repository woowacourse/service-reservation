package com.h3.reservation.common;

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

    public String getName() {
        return name;
    }
}
