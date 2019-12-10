package com.h3.reservation.slackcalendar.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.h3.reservation.calendar.CalendarService;
import com.h3.reservation.slackcalendar.dto.SlackCalendarRetrieveRequest;
import com.h3.reservation.slackcalendar.dto.SlackCalendarRetrieveResponse;
import com.h3.reservation.slackcalendar.dto.SlackCalendarEvent;
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
        List<Event> googleEvents = new ArrayList<>();
        googleEvents.add(createEvent("회의실1/희봉/프로젝트", "2019-12-10T10:30:00.000+09:00", "2019-12-10T12:00:00.000+09:00"));
        googleEvents.add(createEvent("회의실1/코니/회고", "2019-12-10T13:00:00.000+09:00", "2019-12-10T15:30:00.000+09:00"));
        googleEvents.add(createEvent("회의실2/도넛/굴러간다", "2019-12-10T11:00:00.000+09:00", "2019-12-10T12:00:00.000+09:00"));
        googleEvents.add(createEvent("회의실3/버디/회의", "2019-12-10T17:00:00.000+09:00", "2019-12-10T18:00:00.000+09:00"));

        when(calendarService.findReservation(any(), any())).thenReturn(googleEvents);
        String date = "2019-12-10";
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

    private Event createEvent(String summary, String startTime, String endTime) {
        return new Event()
            .setSummary(summary)
            .setStart(new EventDateTime().setDateTime(DateTime.parseRfc3339(startTime)))
            .setEnd(new EventDateTime().setDateTime(DateTime.parseRfc3339(endTime)));
    }
}