package com.h3.reservation.slack.dto.response.reserve;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slack.dto.response.common.CommonResponseFactory;
import com.h3.reservation.slack.dto.response.common.ModalResponse;
import com.h3.reservation.slack.dto.response.common.ModalSubmissionType;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.PlainTextInputElement;
import com.h3.reservation.slack.fragment.element.StaticSelectElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveInputResponseFactory {
    private static final String PREFIX_START = "start";
    private static final String PREFIX_END = "end";

    public static ModalResponse of(String triggerId) {
        DatepickerElement datePicker = new DatepickerElement("datepicker");

        ModalView modalView = new ModalView(
            ModalSubmissionType.RESERVE_INPUT,
            new PlainText("예약하기"),
            new PlainText("예약"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("datepicker_block", new PlainText("예약할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요.*")),
                CommonResponseFactory.generateHourPicker(PREFIX_START),
                CommonResponseFactory.generateMinutePicker(PREFIX_START),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateHourPicker(PREFIX_END),
                CommonResponseFactory.generateMinutePicker(PREFIX_END),
                new InputBlock("meeting_room_block", new PlainText("회의실을 선택하세요."),
                    generateMeetingRoomSelectElement()),
                new InputBlock("description_block", new PlainText("회의 제목을 입력하세요."),
                    new PlainTextInputElement("description", new PlainText("회의 제목"))),
                new InputBlock("name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("name", new PlainText("이름")))
            )
        );
        return new ModalResponse(triggerId, modalView);
    }

    private static StaticSelectElement generateMeetingRoomSelectElement() {
        return new StaticSelectElement(
            new PlainText("회의실"),
            "meeting_room",
            generateMeetingRoomSelectOptions()
        );
    }

    private static List<Option> generateMeetingRoomSelectOptions() {
        return Arrays.stream(MeetingRoom.values())
            .filter(meetingRoom -> !meetingRoom.equals(MeetingRoom.NONE))
            .map(room -> new Option(new PlainText(room.getName()), room.getName()))
            .collect(Collectors.toList());
    }
}
