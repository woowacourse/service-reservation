package com.h3.reservation.slackcalendar.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class Reservation {
    private final Component component;
    private final DateTime time;

    private Reservation(final Component component, final DateTime time) {
        this.component = component;
        this.time = time;
    }

    /**
     * @param date      yyyy-MM-dd
     * @param startTime HH:mm
     * @param endTime   HH:mm
     * @return
     */
    public static Reservation of(final String room, final String booker, final String purpose
        , final String date, final String startTime, final String endTime) {
        return new Reservation(Component.of(room, booker, purpose)
            , DateTime.of(date, startTime, endTime));
    }

    public static Reservation of(final String room, final String booker, final String purpose
        , final LocalDate date, final LocalTime startTime, final LocalTime endTime) {
        return new Reservation(Component.of(room, booker, purpose)
            , DateTime.of(date, startTime, endTime));
    }

    public static Reservation of(final Component component, final DateTime time) {
        return new Reservation(component, time);
    }

    public String getRoom() {
        return component.getRoom();
    }

    public String getBooker() {
        return component.getBooker();
    }

    public String getPurpose() {
        return component.getPurpose();
    }

    public String getFormattedStartTime() {
        return time.getFormattedStartTime();
    }

    public String getFormattedEndTime() {
        return time.getFormattedEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (!Objects.equals(component, that.component)) return false;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        int result = component != null ? component.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
