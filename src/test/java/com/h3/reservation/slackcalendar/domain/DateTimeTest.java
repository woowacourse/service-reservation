package com.h3.reservation.slackcalendar.domain;

import com.h3.reservation.slackcalendar.exception.InvalidTimeRangeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
class DateTimeTest {
    @Test
    void constructor() {
        LocalDate date = LocalDate.of(2019, 12, 10);
        LocalTime startTime = LocalTime.of(13, 30);
        LocalTime endTime = LocalTime.of(15, 0);
        DateTime eventTime = DateTime.of(date, startTime, endTime);

        assertEquals(eventTime, DateTime.of(date, startTime, endTime));
    }

    @Test
    void constructor_string() {
        String date = "2019-12-10";
        String startTime = "13:00";
        String endTime = "15:00";

        DateTime eventTime = DateTime.of(date, startTime, endTime);

        assertEquals(eventTime, DateTime.of(LocalDate.parse(date), LocalTime.parse(startTime), LocalTime.parse(endTime)));
        assertEquals(eventTime.getDate(), LocalDate.of(2019, 12, 10));
    }

    @Test
    void constructor_unformatted_string_exception() {
        String date = "2019-12-10";
        String startTime = "13:00";
        String endTime = "15:00";

        assertThrows(DateTimeParseException.class, () -> DateTime.of("20191210", startTime, endTime));
        assertThrows(DateTimeParseException.class, () -> DateTime.of(date, "1300", endTime));
        assertThrows(DateTimeParseException.class, () -> DateTime.of(date, startTime, "1500"));
    }

    @Test
    void constructor_startTime_later_than_endTime() {
        String date = "2019-12-10";
        String startTime = "15:00";
        String endTime = "13:00";

        assertThrows(InvalidTimeRangeException.class, () -> DateTime.of(date, startTime, endTime));
    }

    @Test
    void getFormattedDate_Time() {
        String date = "2019-12-10";
        String startTime = "13:00";
        String endTime = "15:00";

        DateTime eventTime = DateTime.of(date, startTime, endTime);
        assertEquals(eventTime.getFormattedDate(), date);
        assertEquals(eventTime.getFormattedStartTime(), startTime);
        assertEquals(eventTime.getFormattedEndTime(), endTime);
    }
}