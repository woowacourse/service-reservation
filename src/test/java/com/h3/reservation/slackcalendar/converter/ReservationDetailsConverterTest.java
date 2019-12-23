package com.h3.reservation.slackcalendar.converter;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import com.h3.reservation.utils.InvalidSummaryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationDetailsConverterTest {

    @Test
    void transferReservationDetails() {
        String summary = " 회의실 2 / 버디 / 프로젝트";

        ReservationDetails reservationDetails = ReservationDetailsConverter.toReservationDetails(summary, "/");

        assertEquals(reservationDetails, ReservationDetails.of(MeetingRoom.ROOM2, "버디", "프로젝트"));
    }

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

    @ParameterizedTest
    @MethodSource("unFormattedSummaries")
    void isFormattedError(final String summary) {
        assertThrows(InvalidSummaryException.class, () -> ReservationDetailsConverter.toReservationDetails(summary, "/"));
    }

    private static Stream<String> unFormattedSummaries() {
        return Stream.of(unFormatted);
    }
}