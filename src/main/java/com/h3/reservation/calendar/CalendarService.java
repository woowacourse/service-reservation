package com.h3.reservation.calendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.h3.reservation.calendar.domain.CalendarId;
import com.h3.reservation.calendar.domain.ReservationDateTime;
import com.h3.reservation.calendar.exception.FetchingEventsFailedException;
import com.h3.reservation.calendar.utils.SummaryParser;
import com.h3.reservation.common.MeetingRoom;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private Calendar calendar;

    public CalendarService(final Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Event> findReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId) {
        try {
            Calendar.Events.List eventList = restrictEventsWithFetchingDate(calendarId);
            Events results = eventList.execute();

            return results.getItems().stream()
                    .filter(item -> fetchingDate.isStartTimeEarlierThan(item.getEnd().getDateTime()))
                    .filter(item -> !fetchingDate.isEndTimeEarlierThanOrEqualTo(item.getStart().getDateTime()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new FetchingEventsFailedException(e);
        }
    }

    private Calendar.Events.List restrictEventsWithFetchingDate(final CalendarId calendarId) throws IOException {
        Calendar.Events eventsInCalendar = calendar.events();

        return eventsInCalendar.list(calendarId.getId());
    }

    /**
     * todo reservationDto를 만들자!
     * 특정 시간에 예약된 (즉 예약하면 안되는) 모든 이벤트를 부른 뒤 해당 회의실로 필터링 -> 필터링 했을 때 남아있는게 존재하면 예약 실패! 없으면 예약시킴
     */
    public Event insertEvent(final ReservationDateTime fetchingDate, final CalendarId calendarId, MeetingRoom room, String attendee, String description) throws IOException {
        checkValidReservation(fetchingDate, calendarId, room);

        EventDateTime startTime = fetchingDate.toEventDateTime(fetchingDate.getStartDateTime());
        EventDateTime endTime = fetchingDate.toEventDateTime(fetchingDate.getEndDateTime());

        Event event = new Event()
                .setStart(startTime)
                .setEnd(endTime)
                .setSummary(room + "/" + attendee + "/" + description);

        calendar.events().insert(calendarId.getId(), event);
        return event;
    }

    private void checkValidReservation(final ReservationDateTime fetchingDate, final CalendarId calendarId, MeetingRoom room) {
        List<Event> eventsByTime = findReservation(fetchingDate, calendarId);
        if (isReservedMeetingRoom(room, eventsByTime)) {
            throw new NotAvailableReserveEventException("이미 예약된 방이 있습니다!");
        }
    }

    private boolean isReservedMeetingRoom(MeetingRoom room, List<Event> eventsByTime) {
        return eventsByTime.stream()
                .map(Event::getSummary)
                .map(SummaryParser::parse)
                .filter(Objects::nonNull)
                .map(l -> l.get(0))
                .anyMatch(room.getName()::equals);
    }
}
