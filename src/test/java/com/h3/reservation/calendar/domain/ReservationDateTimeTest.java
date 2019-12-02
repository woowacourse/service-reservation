package com.h3.reservation.calendar.domain;

import com.google.api.client.util.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
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
        ReservationDateTime reservationDateTime = ReservationDateTime.from(successfulFetchingDate);

        assertThat(reservationDateTime).isNotNull();
        assertThat(reservationDateTime.getStartDateTime()).isEqualTo(DateTime.parseRfc3339(successfulFetchingDate + "T00:00:00.000+09:00"));
        assertThat(reservationDateTime.getEndDateTime()).isEqualTo(DateTime.parseRfc3339(successfulFetchingDate + "T23:59:59.999+09:00"));
    }

    @Test
    void create_IllegalDateTimeFormatException() {
        assertThrows(DateTimeParseException.class, () -> ReservationDateTime.from(failedFetchingDate));
    }
}