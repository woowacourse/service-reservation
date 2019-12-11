package com.h3.reservation.slackcalendar.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.slackcalendar.dto.SlackCalendarEvent;
import com.h3.reservation.slackcalendar.dto.SlackCalendarRetrieveRequest;
import com.h3.reservation.slackcalendar.dto.SlackCalendarRetrieveResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void retrieve() {
        String date = "2019-12-10";

        List<Event> googleEvents = new ArrayList<>();
        googleEvents.add(createEvent("회의실1/희봉/프로젝트", date, "10:30", "12:00"));
        googleEvents.add(createEvent("회의실1/코니/회고", date, "13:00", "15:30"));
        googleEvents.add(createEvent("회의실2/도넛/굴러간다", date, "11:00", "12:00"));
        googleEvents.add(createEvent("회의실3/버디/회의", date, "17:00", "18:00"));

        when(calendarService.findReservation(any(), any())).thenReturn(googleEvents);

        String startTime = "10:00";
        String endTime = "18:00";
        SlackCalendarRetrieveRequest request = new SlackCalendarRetrieveRequest(date, startTime, endTime);

        SlackCalendarRetrieveResponse response = slackCalendarService.retrieve(request);
        List<SlackCalendarEvent> slackCalendarEvents = new ArrayList<>();
        slackCalendarEvents.add(new SlackCalendarEvent("회의실1", "희봉", "프로젝트", "10:30", "12:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실1", "코니", "회고", "13:00", "15:30"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실2", "도넛", "굴러간다", "11:00", "12:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실3", "버디", "회의", "17:00", "18:00"));

        assertEquals(response.getSlackCalendarEvents(), slackCalendarEvents);
    }

    /**
     *
     * @param summary       room/booker/purpose
     * @param date          yyyy-MM-dd
     * @param startTime     hh:mm
     * @param endTime       hh:mm
     * @return
     */
    private Event createEvent(String summary, String date, String startTime, String endTime) {
        return new Event()
            .setSummary(summary)
            .setStart(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, startTime))))
            .setEnd(new EventDateTime().setDateTime(DateTime.parseRfc3339(generateDateTime(date, endTime))));
    }

    /**
     *
     * @param date      yyyy-MM-dd
     * @param time      hh:mm
     * @return
     */
    private String generateDateTime(String date, String time) {
        return date + "T" + time + ":00.000+09:00";
    }
}