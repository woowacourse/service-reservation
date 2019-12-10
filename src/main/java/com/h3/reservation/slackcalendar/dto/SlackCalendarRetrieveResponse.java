package com.h3.reservation.slackcalendar.dto;

import java.util.List;
import java.util.Objects;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class SlackCalendarRetrieveResponse {
    private final String date;
    private final String startTime;
    private final String endTime;
    private final List<SlackCalendarEvent> slackCalendarEvents;

    /**
     * @param date      yyyy-MM-dd
     * @param startTime hh:mm
     * @param endTime   hh:mm
     */
    public SlackCalendarRetrieveResponse(String date, String startTime, String endTime, List<SlackCalendarEvent> slackCalendarEvents) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.slackCalendarEvents = slackCalendarEvents;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<SlackCalendarEvent> getSlackCalendarEvents() {
        return slackCalendarEvents;
    }

    public boolean isEventEmpty() {
        return slackCalendarEvents.size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlackCalendarRetrieveResponse that = (SlackCalendarRetrieveResponse) o;

        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(startTime, that.startTime)) return false;
        if (!Objects.equals(endTime, that.endTime)) return false;
        return Objects.equals(slackCalendarEvents, that.slackCalendarEvents);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (slackCalendarEvents != null ? slackCalendarEvents.hashCode() : 0);
        return result;
    }
}
