package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.RetrieveResponse;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Arrays;

public class RetrieveResponseFactory {
    private static final int MIN_TIME = 10;
    private static final int MAX_TIME = 21;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 50;

    public static RetrieveResponse of(String trigger_id) {
        DatepickerElement datePicker = new DatepickerElement("retrieve_datepicker");

        ModalView modalView = new ModalView(
            new PlainText("조회하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("retrieve_datepicker_block", new PlainText("조회할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(
                    "retrieve_start_time_block", "retrieve_start_time", MIN_TIME),
                CommonResponseFactory.generateMinutePicker(
                    "retrieve_start_minute_block", "retrieve_start_minute", MIN_MINUTE
                ),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(
                    "retrieve_end_time_block", "retrieve_end_time", MAX_TIME),
                CommonResponseFactory.generateMinutePicker(
                    "retrieve_end_minute_block", "retrieve_end_minute", MAX_MINUTE
                )
            )
        );
        return new RetrieveResponse(trigger_id, modalView);
    }
}
