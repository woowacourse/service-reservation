package com.h3.reservation.calendar.domain;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import com.h3.reservation.calendar.domain.exception.InvalidDateTimeRangeException;

public class ReservationDateTime {

    private static final String START_TIME_OF_DAY = "00:00:00";
    private static final String END_TIME_OF_DAY = "23:59:59";
    private static final String PREFIX_OF_TIME = "T";
    private static final String TIME_DELIMITER = ":";
    private static final String DEFAULT_SECONDS = "00";
    private static final int MIN_COUNT_OF_TIME_TOKENS = 2;
    private static final String TIME_ZONE = "+09:00";

    private final DateTime startDateTime;
    private final DateTime endDateTime;

    private ReservationDateTime(final DateTime startDateTime, final DateTime endDateTime) {
        if (isFirstTimeEarlierThanOrEqualToSecondTime(endDateTime, startDateTime)) {
            throw new InvalidDateTimeRangeException();
        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * @param fetchingDate The format of fetchingDate : yyyy-MM-dd
     */
    public static ReservationDateTime of(final String fetchingDate) {
        return of(fetchingDate, START_TIME_OF_DAY, END_TIME_OF_DAY);
    }

    /**
     * @param fetchingDate The format of fetchingDate : yyyy-MM-dd
     * @param startTime    The format of fetchingDate : hh:mm(:ss)
     * @param endTime      The format of fetchingDate : hh:mm(:ss)
     */
    public static ReservationDateTime of(final String fetchingDate, final String startTime, final String endTime) {
        String formattedStartTime = createFormattedTimeWithTimeZone(startTime);
        String formattedEndTime = createFormattedTimeWithTimeZone(endTime);

        DateTime startDateTime = DateTime.parseRfc3339(fetchingDate + formattedStartTime);
        DateTime endDateTime = DateTime.parseRfc3339(fetchingDate + formattedEndTime);

        return new ReservationDateTime(startDateTime, endDateTime);
    }

    private static String createFormattedTimeWithTimeZone(final String time) {
        return createFormattedTime(time) + TIME_ZONE;
    }

    private static String createFormattedTime(final String time) {
        String[] tokens = time.split(TIME_DELIMITER);
        if (notExistsSeconds(tokens)) {
            return PREFIX_OF_TIME + time + TIME_DELIMITER + DEFAULT_SECONDS;
        }
        return PREFIX_OF_TIME + time;
    }

    private static boolean notExistsSeconds(final String[] tokens) {
        return tokens.length == MIN_COUNT_OF_TIME_TOKENS;
    }

    private boolean isFirstTimeEarlierThanOrEqualToSecondTime(final DateTime firstDateTime, final DateTime secondDateTime) {
        return firstDateTime.getValue() <= secondDateTime.getValue();
    }

    public boolean isStartTimeEarlierThanOrEqualTo(DateTime dateTime) {
        return isFirstTimeEarlierThanOrEqualToSecondTime(startDateTime, dateTime);
    }

    public boolean isEndTimeEarlierThanOrEqualTo(DateTime dateTime) {
        return isFirstTimeEarlierThanOrEqualToSecondTime(endDateTime, dateTime);
    }

    public boolean isStartTimeEarlierThan(DateTime dateTime) {
        return isFirstTimeEarlierThanSecondTime(startDateTime, dateTime);
    }

    public boolean isEndTimeEarlierThan(DateTime dateTime) {
        return isFirstTimeEarlierThanSecondTime(endDateTime, dateTime);
    }

    private boolean isFirstTimeEarlierThanSecondTime(final DateTime firstDateTime, final DateTime secondDateTime) {
        return firstDateTime.getValue() < secondDateTime.getValue();
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public EventDateTime toEventDateTime(DateTime dateTime) {
        return new EventDateTime().setDateTime(dateTime);
    }

    @Override
    public String toString() {
        return "ReservationDateTime{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
