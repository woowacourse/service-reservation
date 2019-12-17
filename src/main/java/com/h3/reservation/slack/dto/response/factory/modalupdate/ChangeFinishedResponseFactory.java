package com.h3.reservation.slack.dto.response.factory.modalupdate;

import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Collections;

public class ChangeFinishedResponseFactory {
    public static ModalUpdateResponse of() {
        return new ModalUpdateResponse(
            new ModalView(
                "change_result",
                new PlainText("변경하기"),
                new PlainText("확인"),
                Collections.singletonList(new SectionBlock(new PlainText("변경되었습니다.")))
            )
        );
    }
}
