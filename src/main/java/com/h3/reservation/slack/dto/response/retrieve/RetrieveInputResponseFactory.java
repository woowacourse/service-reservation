package com.h3.reservation.slack.dto.response.retrieve;

import com.h3.reservation.slack.dto.response.common.CommonResponseFactory;
import com.h3.reservation.slack.dto.response.common.ModalResponse;
import com.h3.reservation.slack.dto.response.common.ModalSubmissionType;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class RetrieveInputResponseFactory {
    private static final String INIT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String PREFIX_START = "start";
    private static final String PREFIX_END = "end";
    private static final int MIN_HOUR = 10;
    private static final int MAX_HOUR = 21;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 50;

    public static ModalResponse of(String triggerId) {
        DatepickerElement datePicker = new DatepickerElement("datepicker", generateNowDate());

        ModalView modalView = new ModalView(
            ModalSubmissionType.RETRIEVE_INPUT,
            new PlainText("조회하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("datepicker_block", new PlainText("조회할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요.*")),
                CommonResponseFactory.generateHourPickerWithInitValue(PREFIX_START, MIN_HOUR),
                CommonResponseFactory.generateMinutePickerWithInitValue(PREFIX_START, MIN_MINUTE),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요.*")),
                CommonResponseFactory.generateHourPickerWithInitValue(PREFIX_END, MAX_HOUR),
                CommonResponseFactory.generateMinutePickerWithInitValue(PREFIX_END, MAX_MINUTE)
            )
        );
        return new ModalResponse(triggerId, modalView);
    }

    private static String generateNowDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(INIT_DATE_PATTERN));
    }
}
