package com.h3.reservation.slack.dto.response.reserve;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slack.dto.response.common.ModalSubmissionType;
import com.h3.reservation.slack.dto.response.common.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.view.ModalView;
import com.h3.reservation.slackcalendar.domain.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-23
 */
public class ReserveAvailableMeetingResponseFactory {
    public static ModalUpdateResponse of(List<MeetingRoom> meetingRooms, DateTime dateTime) {
        return new ModalUpdateResponse(
            new ModalView(
                ModalSubmissionType.RESERVE_AVAILABLE_MEETINGROOM,
                dateTime.getFormattedDate() + "_" + dateTime.getFormattedStartTime() + "_" + dateTime.getFormattedEndTime(),
                new PlainText("예약하기"),
                new PlainText("확인"),
                generateMeetingRoomBlocks(meetingRooms, dateTime)
            )
        );
    }

    private static List<Block> generateMeetingRoomBlocks(List<MeetingRoom> meetingRooms, DateTime dateTime) {
        List<Block> blocks = new ArrayList<>();
        addTitleBlock(blocks, dateTime);
        addAvailableMeetingRooms(blocks, meetingRooms);
        return blocks;
    }

    private static void addTitleBlock(List<Block> blocks, DateTime dateTime) {
        String date = dateTime.getFormattedDate() + " " + dateTime.getFormattedStartTime() + "-" + dateTime.getFormattedEndTime();
        blocks.add(new SectionBlock(new PlainText(date + "에 예약 가능한 회의실은 다음과 같습니다.")));
        blocks.add(new DividerBlock());
    }

    private static void addAvailableMeetingRooms(List<Block> blocks, List<MeetingRoom> meetingRooms) {
        blocks.addAll(generateAvailableMeetingRooms(meetingRooms));
    }

    private static List<Block> generateAvailableMeetingRooms(List<MeetingRoom> meetingRooms) {
        return meetingRooms.stream()
            .map(ReserveAvailableMeetingResponseFactory::generateAvailableMeetingRoom)
            .collect(Collectors.toList());
    }

    private static SectionBlock generateAvailableMeetingRoom(MeetingRoom meetingRoom) {
        return new SectionBlock(new MrkdwnText("*" + meetingRoom.getName() + "*"),
            new ButtonElement(new PlainText("선택"), "request_reserve_" + meetingRoom.getName()));
    }

}
