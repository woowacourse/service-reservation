package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.ChangeResponse;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.PlainTextInputElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Arrays;

public class ChangeResponseFactory {
    public static ChangeResponse of(String trigger_id) {
        DatepickerElement datePicker = new DatepickerElement("change_datepicker");

        ModalView modalView = new ModalView(
            new PlainText("변경/취소하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("change_datepicker_block", new PlainText("조회할 날짜를 선택하세요."), datePicker),
                new InputBlock("change_name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("change_name", new PlainText("이름")))
            )
        );
        return new ChangeResponse(trigger_id, modalView);
    }
}
