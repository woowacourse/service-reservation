package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.ReserveResponse;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.PlainTextInputElement;
import com.h3.reservation.slack.fragment.element.StaticSelectElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReserveResponseFactory {
    private static final String MEETING_ROOM = "회의실";
    private static final String PREFIX_START = "start";
    private static final String PREFIX_END = "end";
    private static final int NUMBER_OF_MEETING_ROOM = 5;

    public static ReserveResponse of(String triggerId) {
        DatepickerElement datePicker = new DatepickerElement("datepicker");

        ModalView modalView = new ModalView(
            "reserve",
            new PlainText("예약하기"),
            new PlainText("예약"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("datepicker_block", new PlainText("예약할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(PREFIX_START),
                CommonResponseFactory.generateMinutePicker(PREFIX_START),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(PREFIX_END),
                CommonResponseFactory.generateMinutePicker(PREFIX_END),
                new InputBlock("meeting_room_block", new PlainText("회의실을 선택하세요."),
                    generateMeetingRoomSelectElement()),
                new InputBlock("name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("name", new PlainText("이름")))
            )
        );
        return new ReserveResponse(triggerId, modalView);
    }

    private static StaticSelectElement generateMeetingRoomSelectElement() {
        return new StaticSelectElement(
            new PlainText(MEETING_ROOM),
            "meeting_room",
            generateMeetingRoomSelectOptions()
        );
    }

    private static List<Option> generateMeetingRoomSelectOptions() {
        List<Option> options = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_MEETING_ROOM; i++) {
            options.add(new Option(new PlainText(String.format("%s %d", MEETING_ROOM, i)), String.valueOf(i)));
        }
        return options;
    }
}
