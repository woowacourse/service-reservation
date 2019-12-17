package com.h3.reservation.slack.dto.response.factory.modalpush;

import com.h3.reservation.slack.dto.response.ModalResponse;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Collections;

public class CancelPushResponseFactory {
    public static ModalResponse of(String triggerId) {
        ModalView modalView = new ModalView(
            "cancel_request",
            new PlainText("취소하기"),
            new PlainText("네"),
            new PlainText("아니오"),
            Collections.singletonList(
                new SectionBlock(new PlainText("정말 취소하시겠습니까?"))
            )
        );
        return new ModalResponse(triggerId, modalView);
    }
}
