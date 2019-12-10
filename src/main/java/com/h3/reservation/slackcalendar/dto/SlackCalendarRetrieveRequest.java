package com.h3.reservation.slackcalendar.dto;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class SlackCalendarRetrieveRequest {
    private String date;
    private String startTime;
    private String endTime;

    /**
     * @param date      yyyy-mm-dd
     * @param startTime hh:mm
     * @param endTime   hh:mm
     */
    public SlackCalendarRetrieveRequest(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
