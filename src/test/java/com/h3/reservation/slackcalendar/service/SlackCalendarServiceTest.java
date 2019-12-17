package com.h3.reservation.slackcalendar.service;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.calendar.domain.CalendarEvents;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slackcalendar.domain.DateTime;
import com.h3.reservation.slackcalendar.domain.Reservation;
import com.h3.reservation.slackcalendar.domain.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
@ExtendWith(MockitoExtension.class)
class SlackCalendarServiceTest {
    @Mock
    private CalendarService calendarService;

    @InjectMocks
    private SlackCalendarService slackCalendarService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(slackCalendarService, "summaryDelimiter", "/");
    }

    @Test
    void retrieve() {
        String date = "2019-12-10";

        List<com.google.api.services.calendar.model.Event> googleEvents = new ArrayList<>();
        googleEvents.add(createEvent("1", "회의실1/희봉/프로젝트", date, "10:30", "12:00"));
        googleEvents.add(createEvent("2", "회의실2/도넛/굴러간다", date, "11:00", "12:00"));
        googleEvents.add(createEvent("3", "회의실1/코니/회고", date, "13:00", "15:30"));
        googleEvents.add(createEvent("4", "회의실3/버디/회의", date, "17:00", "18:00"));

        when(calendarService.findReservation(any(), any())).thenReturn(new CalendarEvents(googleEvents));

        String startTime = "10:00";
        String endTime = "18:00";
        DateTime retrieveRangeDateTime = DateTime.of(date, startTime, endTime);

        Reservations reservations = slackCalendarService.retrieve(retrieveRangeDateTime);
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(Reservation.of("1", MeetingRoom.ROOM1, "희봉", "프로젝트", date, "10:30", "12:00"));
        reservationList.add(Reservation.of("2", MeetingRoom.ROOM2, "도넛", "굴러간다", date, "11:00", "12:00"));
        reservationList.add(Reservation.of("3", MeetingRoom.ROOM1, "코니", "회고", date, "13:00", "15:30"));
        reservationList.add(Reservation.of("4", MeetingRoom.ROOM3, "버디", "회의", date, "17:00", "18:00"));

        assertEquals(reservations, Reservations.of(reservationList));
    }

    /**
     * @param summary   room/booker/purpose
     * @param date      yyyy-MM-dd
     * @param startTime hh:mm
     * @param endTime   hh:mm
     * @return
     */
    private Event createEvent(String id, String summary, String date, String startTime, String endTime) {
        // TODO : event Id 설정 어떻게 하는지 확인
        return new Event()
            .setId(id)
            .setSummary(summary)
            .setStart(new EventDateTime().setDateTime(com.google.api.client.util.DateTime.parseRfc3339(generateDateTime(date, startTime))))
            .setEnd(new EventDateTime().setDateTime(com.google.api.client.util.DateTime.parseRfc3339(generateDateTime(date, endTime))));
    }

    /**
     * @param date yyyy-MM-dd
     * @param time hh:mm
     * @return
     */
    private String generateDateTime(String date, String time) {
        return date + "T" + time + ":00.000+09:00";
    }
}