package com.h3.reservation.slackcalendar.converter;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import com.h3.reservation.utils.InvalidSummaryException;
import com.h3.reservation.utils.BasicParser;

import java.util.List;

public class ReservationDetailsConverter {

    private static final int INDEX_OF_MEETING_ROOM = 0;
    private static final int INDEX_OF_BOOKER = 1;
    private static final int INDEX_OF_DESCRIPTION = 2;
    private static final int SIZE_OF_SUMMARY_TOKENS = 3;

    private ReservationDetailsConverter() {
    }

    public static ReservationDetails toReservationDetails(final String summary, final String summaryDelimiter) {
        List<String> tokens = BasicParser.parse(summary, summaryDelimiter);
        if (tokens.size() != SIZE_OF_SUMMARY_TOKENS) {
            throw new InvalidSummaryException();
        }

        MeetingRoom meetingRoom = MeetingRoom.findByName(tokens.get(INDEX_OF_MEETING_ROOM));
        if (MeetingRoom.NONE.equals(meetingRoom)) {
            throw new InvalidSummaryException();
        }

        return ReservationDetails.of(meetingRoom
                , tokens.get(INDEX_OF_BOOKER), tokens.get(INDEX_OF_DESCRIPTION));
    }
}
