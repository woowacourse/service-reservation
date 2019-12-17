package com.h3.reservation.slackcalendar.converter;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slackcalendar.domain.Reservation;

import java.util.Arrays;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class ReservationConverter {
    private static final int SUMMARY_VALID_SIZE = 3;
    private static final int SUMMARY_ROOM_INDEX = 0;
    private static final int SUMMARY_BOOKER_INDEX = 1;
    private static final int SUMMARY_PURPOSE_INDEX = 2;
    private static final String DATETIME_REGEX = "[T|.]";
    private static final String TIME_REGEX = ":";
    private static final int DATETIME_DATE_INDEX = 0;
    private static final int DATETIME_TIME_INDEX = 1;
    private static final int TIME_HOUR_INDEX = 0;
    private static final int TIME_MINUTE_INDEX = 1;

    private ReservationConverter() {
    }

    public static boolean isFormatted(String summary, String summaryDelimiter) {
        return isValidateFormat(splitWithTrim(summary, summaryDelimiter));
    }

    private static boolean isValidateFormat(String[] summaries) {
        return isValidSize(summaries) && isValidMeetingRoom(summaries[SUMMARY_ROOM_INDEX].replace(" ", ""));
    }

    private static boolean isValidSize(String[] summaries) {
        return summaries.length == SUMMARY_VALID_SIZE;
    }

    private static boolean isValidMeetingRoom(String meetingRoom) {
        return !MeetingRoom.NONE.equals(MeetingRoom.findByName(meetingRoom));
    }

    public static Reservation toReservation(Event event, String summaryDelimiter) {
        String[] summary = splitWithTrim(event.getSummary(), summaryDelimiter);
        String startDateTime = event.getStart().getDateTime().toString();
        String endDateTime = event.getEnd().getDateTime().toString();

        return Reservation.of(
            event.getId(), MeetingRoom.findByName(summary[SUMMARY_ROOM_INDEX].replace(" ", "")), summary[SUMMARY_BOOKER_INDEX]
            , summary[SUMMARY_PURPOSE_INDEX], parseDate(startDateTime), parseTime(startDateTime), parseTime(endDateTime)
        );
    }

    private static String parseDate(String dateTime) {
        String[] dateTimes = splitWithTrim(dateTime, DATETIME_REGEX);
        return dateTimes[DATETIME_DATE_INDEX];
    }

    /**
     * @param dateTime 2019-12-10T10:30:00.000+09:00
     * @return
     */
    private static String parseTime(String dateTime) {
        String[] times = splitWithTrim(dateTime, DATETIME_REGEX);
        times = splitWithTrim(times[DATETIME_TIME_INDEX], TIME_REGEX);
        return times[TIME_HOUR_INDEX] + ":" + times[TIME_MINUTE_INDEX];
    }

    private static String[] splitWithTrim(String summary, String summaryDelimiter) {
        return Arrays.stream(summary.split(summaryDelimiter))
            .map(String::trim)
            .toArray(String[]::new);
    }
}
