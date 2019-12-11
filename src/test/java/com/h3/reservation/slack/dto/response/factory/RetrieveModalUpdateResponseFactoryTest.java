package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slackcalendar.dto.SlackCalendarEvent;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("room을 key로 맵을 만들었을 때 room이 정렬이 되었는지 확인하는 학습 테스트")
    void groupby() {
        List<SlackCalendarEvent> slackCalendarEvents = new ArrayList<>();
        slackCalendarEvents.add(new SlackCalendarEvent("회의실3", "희봉", "프로젝트", "10:30", "12:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실1", "코니", "회고", "13:00", "15:30"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실2", "도넛", "굴러간다", "13:00", "14:00"));
        slackCalendarEvents.add(new SlackCalendarEvent("회의실3", "버디", "회의", "13:00", "14:00"));

        TreeMap<String, List<SlackCalendarEvent>> listMap = slackCalendarEvents.stream()
            .collect(groupingBy(SlackCalendarEvent::getRoom, TreeMap::new, Collectors.toList()));
        assertEquals(listMap.size(), 3);
        listMap.forEach((key, value) -> System.out.println(key));
    }
}