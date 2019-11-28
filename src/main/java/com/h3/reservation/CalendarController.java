package com.h3.reservation;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class CalendarController {

    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);

    @Value("${calendar.id}")
    private String calenderId;

    private Calendar calendar;

    public CalendarController(Calendar calendar) {
        this.calendar = calendar;
    }

    @GetMapping("/find")
    public ResponseEntity<Events> findAllEvents() throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();
        Calendar.Events.List eventsList = eventsInCalendar.list(calenderId);
        Events fetchedSchedules = eventsList.execute();

        for (Event schedule : fetchedSchedules.getItems()) {
            log.info("만든이 - {}, 제목 - {}, 설명 - {}, 위치 - {}, 시작:{}, 끝:{}",
                    schedule.getCreator(), schedule.getSummary(), schedule.getDescription(), schedule.getLocation()
                    , schedule.getStart(), schedule.getEnd());
        }

        return new ResponseEntity<>(fetchedSchedules, HttpStatus.OK);
    }
}
