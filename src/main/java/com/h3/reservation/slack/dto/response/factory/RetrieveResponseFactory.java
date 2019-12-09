package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.RetrieveResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.StaticSelectElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetrieveResponseFactory {
    public static RetrieveResponse of(String trigger_id) {
        DatepickerElement datePicker = new DatepickerElement("retrieve_datepicker");

        ModalView modalView = new ModalView(
            new PlainText("조회하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("retrieve_datepicker_block", new PlainText("조회할 날짜를 선택하세요."), datePicker),
                new SectionBlock("retrieve_start_time_label_block", new MrkdwnText("*시작 시간을 선택하세요*")),
                generateTimePicker(
                    "retrieve_start_time", "retrieve_start_minute", 10, 0
                ),
                new SectionBlock("retrieve_end_time_label_block", new MrkdwnText("*종료 시간을 선택하세요*")),
                generateTimePicker(
                    "retrieve_end_time", "retrieve_end_minute", 21, 50
                )
            )
        );
        return new RetrieveResponse(trigger_id, modalView);
    }

    private static ActionsBlock generateTimePicker(String timeActionId, String minuteActionId,
                                            int initialTime, int initialMinute) {
        return new ActionsBlock(
            "retrieve_timepicker_block",
            Arrays.asList(
                new StaticSelectElement(
                    new PlainText("시"),
                    timeActionId,
                    new Option(new PlainText(initialTime + "시"), String.valueOf(initialTime)),
                    generateTimeSelect()
                ),
                new StaticSelectElement(
                    new PlainText("분"),
                    minuteActionId,
                    new Option(new PlainText(initialMinute + "분"), String.valueOf(initialMinute)),
                    generateMinuteSelect()
                )
            )
        );
    }

    private static List<Option> generateTimeSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 10; i <= 21; i++) {
            options.add(new Option(new PlainText(i + "시"), String.valueOf(i)));
        }
        return options;
    }

    private static List<Option> generateMinuteSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i <= 50; i += 10) {
            options.add(new Option(new PlainText(i + "분"), String.valueOf(i)));
        }
        return options;
    }
}
