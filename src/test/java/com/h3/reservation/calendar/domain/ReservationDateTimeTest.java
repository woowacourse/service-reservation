package com.h3.reservation.calendar.domain;

import com.google.api.client.util.DateTime;
import com.h3.reservation.calendar.domain.exception.InvalidDateTimeRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationDateTimeTest {

    private String successfulFetchingDate;
    private String failedFetchingDate;

    @BeforeEach
    void setUp() {
        successfulFetchingDate = "2019-12-01";
        failedFetchingDate = "2019.12.01";
    }

    @Test
    void from() {
        ReservationDateTime reservationDateTime = ReservationDateTime.of(successfulFetchingDate);

        assertThat(reservationDateTime).isNotNull();
        assertThat(reservationDateTime.getStartDateTime()).isEqualTo(DateTime.parseRfc3339(configureTimeZone(successfulFetchingDate + "T00:00:00")));
        assertThat(reservationDateTime.getEndDateTime()).isEqualTo(DateTime.parseRfc3339(configureTimeZone(successfulFetchingDate + "T23:59:59")));
    }

    @Test
    void from_startTime_endTime() {
        ReservationDateTime reservationDateTime = ReservationDateTime.of(successfulFetchingDate, "16:00", "18:00");

        assertThat(reservationDateTime).isNotNull();
        assertThat(reservationDateTime.getStartDateTime()).isEqualTo(DateTime.parseRfc3339(configureTimeZone(successfulFetchingDate + "T16:00:00")));
        assertThat(reservationDateTime.getEndDateTime()).isEqualTo(DateTime.parseRfc3339(configureTimeZone(successfulFetchingDate + "T18:00:00")));
    }

    @Test
    void create_IllegalDateTimeFormatException() {
        assertThrows(NumberFormatException.class, () -> ReservationDateTime.of(failedFetchingDate));
        assertThrows(NumberFormatException.class, () -> ReservationDateTime.of(successfulFetchingDate, "16-00", "17:00"));
    }

    @Test
    void create_InvalidDateTimeRangeException() {
        assertThrows(InvalidDateTimeRangeException.class, () -> ReservationDateTime.of(successfulFetchingDate, "16:30", "16:29"));
        assertThrows(InvalidDateTimeRangeException.class, () -> ReservationDateTime.of(successfulFetchingDate, "16:30", "16:30"));
        assertDoesNotThrow(() -> ReservationDateTime.of(successfulFetchingDate, "16:30", "16:31"));
    }

    private String configureTimeZone(String time) {
        return time + "+09:00";
    }
}