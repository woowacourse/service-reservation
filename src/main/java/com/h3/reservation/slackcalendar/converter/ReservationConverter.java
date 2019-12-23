package com.h3.reservation.slackcalendar.converter;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.common.ReservationDetails;
import com.h3.reservation.slackcalendar.domain.Reservation;
import com.h3.reservation.slackcalendar.exception.InvalidTimeRangeException;
import com.h3.reservation.utils.BasicParser;
import com.h3.reservation.utils.InvalidSummaryException;

import java.util.List;
import java.util.Optional;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
public class ReservationConverter {
    private static final String DATETIME_REGEX = "[T|.]";
    private static final String TIME_REGEX = ":";
    private static final int INDEX_OF_DATETIME_DATE = 0;
    private static final int INDEX_OF_DATETIME_TIME = 1;
    private static final int INDEX_OF_HOUR = 0;
    private static final int INDEX_OF_MINUTE = 1;

    private ReservationConverter() {
    }

    public static Optional<Reservation> toReservation(Event event, String summaryDelimiter) {
        try {
            String summary = event.getSummary();
            ReservationDetails reservationDetails = ReservationDetailsConverter.toReservationDetails(summary, summaryDelimiter);

            String startDateTime = event.getStart().getDateTime().toString();
            String endDateTime = event.getEnd().getDateTime().toString();

            return Optional.of(Reservation.of(event.getId(), reservationDetails, parseDate(startDateTime)
                    , parseTime(startDateTime), parseTime(endDateTime)));
        } catch (InvalidSummaryException | InvalidTimeRangeException e) {
            return Optional.empty();
        }
    }

    private static String parseDate(String dateTime) {
        List<String> tokens = BasicParser.parse(dateTime, DATETIME_REGEX);

        return tokens.get(INDEX_OF_DATETIME_DATE);
    }

    /**
     * @param dateTime 2019-12-10T10:30:00.000+09:00
     * @return
     */
    private static String parseTime(String dateTime) {
        List<String> tokens = BasicParser.parse(dateTime, DATETIME_REGEX);
        tokens = BasicParser.parse(tokens.get(INDEX_OF_DATETIME_TIME), TIME_REGEX);

        return tokens.get(INDEX_OF_HOUR) + ":" + tokens.get(INDEX_OF_MINUTE);
    }
}
