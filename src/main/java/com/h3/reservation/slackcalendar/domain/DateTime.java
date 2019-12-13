package com.h3.reservation.slackcalendar.domain;

import com.h3.reservation.slackcalendar.exception.InvalidTimeRangeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class DateTime {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm";
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    private DateTime(final LocalDate date, final LocalTime startTime, final LocalTime endTime) {
        checkValidate(startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void checkValidate(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new InvalidTimeRangeException();
        }
    }

    public static DateTime of(final LocalDate date, final LocalTime startTime, final LocalTime endTime) {
        return new DateTime(date, startTime, endTime);
    }

    /**
     * @param date      yyyy-MM-dd
     * @param startTime HH:mm(:ss)
     * @param endTime   HH:mm(:ss)
     * @return
     */
    public static DateTime of(final String date, final String startTime, final String endTime) {
        return new DateTime(LocalDate.parse(date), LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    /**
     * @param date      yyyy-MM-dd
     * @return
     */
    public static DateTime of(final String date, final LocalTime startTime, final LocalTime endTime) {
        return new DateTime(LocalDate.parse(date), startTime, endTime);
    }

    /**
     * @return yyyy-MM-dd
     */
    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * @return HH:mm
     */
    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    /**
     * @return HH:mm
     */
    public String getFormattedEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    @Override
    public String toString() {
        return "DateTime{" +
            "date=" + date +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTime eventTime = (DateTime) o;

        if (!Objects.equals(date, eventTime.date)) return false;
        if (!Objects.equals(startTime, eventTime.startTime)) return false;
        return Objects.equals(endTime, eventTime.endTime);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
