package com.h3.reservation.slackcalendar.domain;

import com.h3.reservation.slackcalendar.exception.InvalidTimeRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @DisplayName("LocalTime 크기를 확인하는 학습테스트")
    void local_time_later_than() {
        LocalTime startTime = LocalTime.of(13, 0);
        LocalTime endTime = LocalTime.of(13, 1);

        assertTrue(startTime.isBefore(endTime));
    }

    @Test
    @DisplayName("LocalDate get 포멧 확인하는 학습테스트")
    void local_date_get_formatted() {
        String date = "2019-12-10";
        LocalDate localDate = LocalDate.parse(date);
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(formattedDate, date);

        String time = "13:00";
        LocalTime localTime = LocalTime.parse(time);
        String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        assertEquals(formattedTime, time);
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