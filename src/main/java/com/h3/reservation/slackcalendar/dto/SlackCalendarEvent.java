package com.h3.reservation.slackcalendar.dto;

import java.util.Objects;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class SlackCalendarEvent {
    private String room;
    private String booker;
    private String purpose;
    private String startTime;
    private String endTime;

    /**
     * @param startTime hh:mm
     * @param endTime   hh:mm
     */
    public SlackCalendarEvent(String room, String booker, String purpose, String startTime, String endTime) {
        this.room = room;
        this.booker = booker;
        this.purpose = purpose;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public String getBooker() {
        return booker;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "SlackCalendarEvent{" +
            "room='" + room + '\'' +
            ", booker='" + booker + '\'' +
            ", purpose='" + purpose + '\'' +
            ", startTime='" + startTime + '\'' +
            ", endTime='" + endTime + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlackCalendarEvent that = (SlackCalendarEvent) o;

        if (!Objects.equals(room, that.room)) return false;
        if (!Objects.equals(booker, that.booker)) return false;
        if (!Objects.equals(purpose, that.purpose)) return false;
        if (!Objects.equals(startTime, that.startTime)) return false;
        return Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        int result = room != null ? room.hashCode() : 0;
        result = 31 * result + (booker != null ? booker.hashCode() : 0);
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
