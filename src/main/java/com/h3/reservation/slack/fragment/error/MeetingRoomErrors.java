package com.h3.reservation.slack.fragment.error;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-12
 */
public class MeetingRoomErrors extends Errors {
    private String meetingRoomBlock;

    public MeetingRoomErrors() {
    }

    public MeetingRoomErrors(String meetingRoomBlock) {
        this.meetingRoomBlock = meetingRoomBlock;
    }

    public String getMeetingRoomBlock() {
        return meetingRoomBlock;
    }
}
