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
                    "retrieve_start_time_block", "retrieve_start_time", 10),
                CommonResponseFactory.generateMinutePicker(
                    "retrieve_start_minute_block", "retrieve_start_minute", 0
                ),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateTimePicker(
                    "retrieve_end_time_block", "retrieve_end_time", 21),
                CommonResponseFactory.generateMinutePicker(
                    "retrieve_end_minute_block", "retrieve_end_minute", 50
                )
            )
        );
        return new RetrieveResponse(trigger_id, modalView);
    }
}
