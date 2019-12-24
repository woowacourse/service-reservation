package com.h3.reservation.slackcalendar.converter;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import com.h3.reservation.utils.BasicParser;
import com.h3.reservation.utils.InvalidSummaryException;

import java.util.List;

public class ReservationDetailsConverter {

    private static final int INDEX_OF_MEETING_ROOM = 0;
    private static final int INDEX_OF_BOOKER = 1;
    private static final int INDEX_OF_DESCRIPTION = 2;
    private static final String EMPTY_DESCRIPTION = "";

    private ReservationDetailsConverter() {
    }

    public static ReservationDetails toReservationDetails(final String summary, final String summaryDelimiter) {
        List<String> tokens = BasicParser.parse(summary, summaryDelimiter);

        MeetingRoom meetingRoomName = findMeetingRoom(tokens);
        String booker = findBooker(tokens);
        String description = findDescription(tokens);

        return ReservationDetails.of(meetingRoomName, booker, description);
    }

    private static MeetingRoom findMeetingRoom(final List<String> tokens) {
        String meetingRoomName = extractToken(tokens, INDEX_OF_MEETING_ROOM);
        MeetingRoom meetingRoom = MeetingRoom.findByName(meetingRoomName);

        if (MeetingRoom.NONE.equals(meetingRoom)) {
            throw new InvalidSummaryException();
        }
        return meetingRoom;
    }

    private static String findBooker(final List<String> tokens) {
        return extractToken(tokens, INDEX_OF_BOOKER);
    }

    private static String findDescription(final List<String> tokens) {
        try {
            return extractToken(tokens, INDEX_OF_DESCRIPTION);
        } catch (InvalidSummaryException e) {
            return EMPTY_DESCRIPTION;
        }
    }

    private static String extractToken(final List<String> tokens, final int index) {
        try {
            return tokens.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidSummaryException();
        }
    }
}
