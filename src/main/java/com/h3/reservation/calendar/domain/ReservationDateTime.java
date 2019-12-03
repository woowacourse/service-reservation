package com.h3.reservation.calendar.domain;

import com.google.api.client.util.DateTime;
import com.h3.reservation.calendar.domain.exception.InvalidDateTimeRangeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservationDateTime {

    private static final String START_TIME_OF_DAY = "00:00:00";
    private static final String END_TIME_OF_DAY = "23:59:59";

    private final DateTime startDateTime;
    private final DateTime endDateTime;

    private ReservationDateTime(final DateTime startDateTime, final DateTime endDateTime) {
        if (isStartTimeBiggerThanOrEqualToEndTime(startDateTime, endDateTime)) {
            throw new InvalidDateTimeRangeException();
        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private boolean isStartTimeBiggerThanOrEqualToEndTime(final DateTime startDateTime, final DateTime endDateTime) {
        return startDateTime.getValue() >= endDateTime.getValue();
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
        LocalDate localDate = LocalDate.parse(fetchingDate);

        LocalDateTime startOfEvent = LocalDateTime.of(localDate, LocalTime.parse(startTime));
        LocalDateTime endOfEvent = LocalDateTime.of(localDate, LocalTime.parse(endTime));

        DateTime startDateTime = createDateTime(startOfEvent);
        DateTime endDateTime = createDateTime(endOfEvent);

        return new ReservationDateTime(startDateTime, endDateTime);
    }

    private static DateTime createDateTime(final LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return new DateTime(date);
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }
}
