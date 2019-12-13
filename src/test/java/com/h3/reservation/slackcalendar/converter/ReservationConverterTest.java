package com.h3.reservation.slackcalendar.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-13
 */
class ReservationConverterTest {
    private final String summaryDelimiter = "/";
    private static String[] unFormatted = {
        "회의실/제목/목적"
        , "회의실//목적"
        , "회의실/제목/"
        , "회의실2//"
        , "//"
        , "회의실2,//제목/"
        , "회의실2,제목,목적제목/"
        , "으아앙"
    };

    @Test
    void isFormatted() {
        String summary = "회의실2/제목/목적";

        assertTrue(ReservationConverter.isFormatted(summary, summaryDelimiter));
    }

    @ParameterizedTest
    @MethodSource("unFormattedSummaries")
    void isFormattedError(final String summary) {
        assertFalse(ReservationConverter.isFormatted(summary, summaryDelimiter));
    }

    private static Stream<String> unFormattedSummaries() {
        return Stream.of(unFormatted);
    }
}