package com.h3.reservation;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class CalendarController {
    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);
    private static final String CALENDER_ID = "lrcmnt5smcksr6om6leb4qjfds@group.calendar.google.com";

    private Calendar calendar;

    public CalendarController(Calendar calendar) {
        this.calendar = calendar;
    }

    @GetMapping("/find")
    public ResponseEntity<Events> findAllEvents() throws IOException {
        Events events = calendar.events().list(CALENDER_ID).execute();
        for (Event event : events.getItems()) {
            log.info("만든이 - {}, 제목 - {}, 설명 - {}, 위치 - {}, 시작:{}, 끝:{}",
                    event.getCreator(), event.getSummary(), event.getDescription(), event.getLocation(), event.getStart(), event.getEnd());
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
