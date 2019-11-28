package com.h3.reservation;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import static com.h3.reservation.CalendarQuickStart.CALENDER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalendarControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CalendarControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @InjectMocks
    private CalendarController calendarController;

    @Mock
    private Calendar calendar;

    @Test
    void 전체_이벤트_조회_리스트() throws IOException {
        Events events = new Events();
        events.setAccessRole("owner");
        events.setDescription("임시 캘린더~");
        events.setItems(Arrays.asList(makeEvent()));

        Mono<Events> monoEvents = Mono.just(events);
        log.info("이벤츠 : {}", events);
        when(calendar.events().list(CALENDER_ID).execute()).thenReturn(events);

        webTestClient.get().uri("/find")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(res -> {
            String body = new String(res.getResponseBody());
            assertThat(body).isNotEmpty();
        }).jsonPath("$..description").isEqualTo("임시 캘린더~");
    }

    public static Event makeEvent(){
        Event event = new Event()
                .setSummary("회의실4/스터디/버디")
                .setLocation("잠실 본동")
                .setDescription("스터디할거에여");

        DateTime startDateTime = new DateTime(new Date(), TimeZone.getDefault());

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul");

        event.setStart(start);

        DateTime endDateTime = new DateTime(new Date(), TimeZone.getDefault());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul");

        event.setEnd(end);

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));

        event.setReminders(reminders);

        return event;
    }

}