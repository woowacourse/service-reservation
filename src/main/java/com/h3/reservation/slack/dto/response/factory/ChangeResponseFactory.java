package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.ChangeResponse;
import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.PlainTextInputElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Arrays;
import java.util.Collections;

public class ChangeResponseFactory {
    public static ChangeResponse of(String triggerId) {
        DatepickerElement datePicker = new DatepickerElement("datepicker");

        ModalView modalView = new ModalView(
            "change",
            new PlainText("변경/취소하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock("datepicker_block", new PlainText("변경/취소할 날짜를 선택하세요."), datePicker),
                new InputBlock("name_block", new PlainText("예약자 이름을 입력하세요."),
                    new PlainTextInputElement("name", new PlainText("이름")))
            )
        );
        return new ChangeResponse(triggerId, modalView);
    }

    public static ChangeResponse beta(String triggerId) {
        ModalView modalView = new ModalView(
            "change",
            new PlainText("변경/취소하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Collections.singletonList(new ContextBlock(
                Collections.singletonList(new MrkdwnText("아직 개발중입니다. 커밍쑨~~"))
            ))
        );

        return new ChangeResponse(triggerId, modalView);
    }
}
