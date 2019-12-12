package com.h3.reservation.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-12
 */
public class LocalDateTimeTest {
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
}
