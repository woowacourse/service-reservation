package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slackcalendar.dto.SlackCalendarEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-10
 */
class RetrieveModalUpdateResponseFactoryTest {
    @Test
    void groupby() {
        List<SlackCalendarEvent> slackCalendarEvents = new ArrayList<>();
        slackCalendarEvents.add(new SlackCalendarEvent("회의실1", "희봉", "프로젝트", "19:30", "21:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실1", "코니", "회고", "22:00", "00:30"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실2", "도넛", "굴러간다", "20:00", "21:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실3", "버디", "회의", "02:00", "03:00"));

        TreeMap<String, List<SlackCalendarEvent>> listMap = slackCalendarEvents.stream().collect(groupingBy(SlackCalendarEvent::getRoom, TreeMap::new, Collectors.toList()));
        assertEquals(listMap.size(), 3);
        listMap.forEach((key, value) -> System.out.println(key));
    }
}