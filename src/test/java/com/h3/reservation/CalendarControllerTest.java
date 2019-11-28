package com.h3.reservation;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalendarControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CalendarControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @InjectMocks
    private CalendarController calendarController;

    @MockBean
    private Calendar calendar;

    @Mock
    private Calendar.Events events;

    @Mock
    private Calendar.Events.List list;

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        String summary = "회의실4/스터디/버디";
        String location = "잠실 본동";
        String description = "스터디할거에여";

        when(calendar.events()).thenReturn(events);
        when(events.list(anyString())).thenReturn(list);

        Events eventsInCalendar = new Events();
        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);
        eventsInCalendar.setItems(Collections.singletonList(event));

        when(list.execute()).thenReturn(eventsInCalendar);

        webTestClient.get().uri("/find")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.items..summary").isEqualTo(summary)
                .jsonPath("$.items..location").isEqualTo(location)
                .jsonPath("$.items..description").isEqualTo(description);
    }
}