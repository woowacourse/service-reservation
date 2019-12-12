package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.Collections;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class ReserveModalUpdateResponseFactory {
    public static ModalUpdateResponse of() {
        return new ModalUpdateResponse(
            new ModalView(
                "reservation_result",
                new PlainText("예약하기"),
                new PlainText("확인"),
                Collections.singletonList(
                    new SectionBlock(
                        new PlainText("예약하기는 아직 기능이 없습니다. 커밍쑨~")
                    )
                )
            )
        );
    }
}