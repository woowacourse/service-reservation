package com.h3.reservation.slackcalendar.exception;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class InvalidTimeRangeException extends RuntimeException {
    public InvalidTimeRangeException() {
        super("시작시간은 종료시간보다 빨라야 합니다.");
    }
}
