package com.h3.reservation.slack.dto.response.changeandcancel;

import com.h3.reservation.slack.dto.response.common.ModalResponse;
import com.h3.reservation.slack.dto.response.common.ModalSubmissionType;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.PlainTextInputElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ChangeAndCancelInputResponseFactory {
    private static final String INIT_DATE_PATTERN = "yyyy-MM-dd";

    public static ModalResponse of(String triggerId) {
        DatepickerElement datePicker = new DatepickerElement("datepicker", generateNowDate());

        ModalView modalView = new ModalView(
            ModalSubmissionType.CHANGE_AND_CANCEL_INPUT,
            new PlainText("변경/취소하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("datepicker_block", new PlainText("변경/취소할 날짜를 선택하세요."), datePicker),
                new InputBlock("name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("name", new PlainText("이름")))
            )
        );
        return new ModalResponse(triggerId, modalView);
    }

    private static String generateNowDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(INIT_DATE_PATTERN));
    }
}
