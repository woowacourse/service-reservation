package com.h3.reservation.slackcalendar.converter;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slackcalendar.domain.Reservation;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class ReservationConverter {
    private static final int SUMMARY_ROOM_INDEX = 0;
    private static final int SUMMARY_BOOKER_INDEX = 1;
    private static final int SUMMARY_PURPOSE_INDEX = 2;
    private static final String DATETIME_REGEX = "[T|.]";
    private static final String TIME_REGEX = ":";
    private static final int DATETIME_DATE_INDEX = 0;
    private static final int DATETIME_TIME_INDEX = 1;
    private static final int TIME_HOUR_INDEX = 0;
    private static final int TIME_MINUTE_INDEX = 1;

    @Value("${calendar.summary.delimiter:/}")
    private static String summaryDelimiter;

    private ReservationConverter() {
    }

    public static Reservation toReservation(Event event) {
        String[] summary = event.getSummary().split(summaryDelimiter);
        String startDateTime = event.getStart().getDateTime().toString();
        String endDateTime = event.getEnd().getDateTime().toString();

        return Reservation.of(
            MeetingRoom.of(summary[SUMMARY_ROOM_INDEX]), summary[SUMMARY_BOOKER_INDEX], summary[SUMMARY_PURPOSE_INDEX]
            , parseDate(startDateTime), parseTime(startDateTime), parseTime(endDateTime)
        );
    }

    private static String parseDate(String dateTime) {
        String[] dateTimes = dateTime.split(DATETIME_REGEX);
        return dateTimes[DATETIME_DATE_INDEX];
    }

    /**
     * @param dateTime 2019-12-10T10:30:00.000+09:00
     * @return
     */
    private static String parseTime(String dateTime) {
        String[] times = dateTime.split(DATETIME_REGEX);
        times = times[DATETIME_TIME_INDEX].split(TIME_REGEX);
        return times[TIME_HOUR_INDEX] + ":" + times[TIME_MINUTE_INDEX];
    }
}
