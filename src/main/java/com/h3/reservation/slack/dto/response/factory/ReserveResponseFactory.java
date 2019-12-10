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
    public static ReserveResponse of(String trigger_id) {
        DatepickerElement datePicker = new DatepickerElement("reserve_datepicker");

        ModalView modalView = new ModalView(
            new PlainText("예약하기"),
            new PlainText("예약"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("reserve_datepicker_block", new PlainText("예약할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(
                    "reserve_start_timepicker_block", "reserve_start_time", "reserve_start_minute", 10, 0
                ),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(
                    "reserve_end_timepicker_block", "reserve_end_time", "reserve_end_minute", 12, 0
                ),
                new InputBlock("reserve_meetingroom_block", new PlainText("회의실을 선택하세요."),
                    generateMeetingRoomSelectElement()),
                new InputBlock("reserve_name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("reserve_name", new PlainText("이름")))
            )
        );
        return new ReserveResponse(trigger_id, modalView);
    }

    private static StaticSelectElement generateMeetingRoomSelectElement() {
        return new StaticSelectElement(
            new PlainText("회의실"),
            "reserve_meetingroom",
            new Option(new PlainText("회의실 1"), String.valueOf(1)),
            generateMeetingRoomSelectOptions()
        );
    }

    private static List<Option> generateMeetingRoomSelectOptions() {
        List<Option> options = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            options.add(new Option(new PlainText("회의실 " + i), String.valueOf(i)));
        }
        return options;
    }
}
