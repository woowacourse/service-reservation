package com.h3.reservation.common;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-23
 */
public class NotFoundAvailableMeetingRoomException extends RuntimeException {
    public NotFoundAvailableMeetingRoomException() {
        super("예약 가능한 회의실이 없습니다.");
    }
}
