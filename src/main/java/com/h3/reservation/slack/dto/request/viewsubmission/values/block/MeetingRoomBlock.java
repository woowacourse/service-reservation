package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class MeetingRoomBlock {
    private MeetingRoom meetingRoom;

    public MeetingRoomBlock() {
    }

    public MeetingRoomBlock(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }
}
