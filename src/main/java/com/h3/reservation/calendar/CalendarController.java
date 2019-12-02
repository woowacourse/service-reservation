package com.h3.reservation.calendar;

import com.google.api.services.calendar.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CalendarController {

    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);

    private final CalendarService calendarService;

    public CalendarController(final CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/find")
    public List<Event> findAllEvents(@RequestParam String fetchingDate) {
        log.debug("fetching date : {}", fetchingDate);

        List<Event> fetchedSchedule = calendarService.findReservation(fetchingDate);

        for (Event schedule : fetchedSchedule) {
            log.info("예약 내역: {}, 시작시간: {}, 종료시간: {}", schedule.getSummary(), schedule.getStart(), schedule.getEnd());
        }

        return fetchedSchedule;
    }
}
