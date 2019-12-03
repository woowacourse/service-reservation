package com.h3.reservation.calendar.domain;

import com.google.api.client.util.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ReservationDateTime {

    private final DateTime startDateTime;
    private final DateTime endDateTime;

    private ReservationDateTime(final DateTime startDateTime, final DateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * @param fetchingDate The format of fetchingDate : yyyy-MM-dd
     */
    public static ReservationDateTime from(final String fetchingDate) {
        LocalDate localDate = LocalDate.parse(fetchingDate);

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = LocalTime.MAX.atDate(localDate);

        DateTime startDateTime = createDateTime(startOfDay);
        DateTime endDateTime = createDateTime(endOfDay);

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
