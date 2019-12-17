package com.h3.reservation.slack.dto.response.factory.modalupdate;

import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.*;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class ChangeModalUpdateResponseFactory {
    public static ModalUpdateResponse of() {
        return new ModalUpdateResponse(
            new ModalView(
                "change_result",
                new PlainText("변경/취소하기"),
                new PlainText("확인"),
                generateBlocks()
            )
        );
    }

    private static List<Block> generateBlocks() {
        List<Block> blocks = new ArrayList<>();
        addRoomBlock("회의실 1", blocks);
        addReservationBlock(blocks);
        return blocks;
    }

    private static void addRoomBlock(String room, List<Block> blocks) {
        blocks.addAll(generateRoom(room));
    }

    private static List<Block> generateRoom(String room) {
        return Arrays.asList(
            new DividerBlock(),
            new ContextBlock(
                Collections.singletonList(
                    new MrkdwnText("*" + room + "*")
                )
            ),
            new DividerBlock()
        );
    }

    private static void addReservationBlock(List<Block> blocks) {
        blocks.add(
            new SectionBlock(
                new MrkdwnText("*프로젝트*\n2019-12-12 12:00-13:00 - 버디")
            )
        );
        blocks.add(
            new ActionsBlock(
                "change_block",
                Arrays.asList(
                    new ButtonElement(new PlainText("변경"), "request_change_id", "primary"),
                    new ButtonElement(new PlainText("취소"), "request_cancel_id", "danger")
                )
            )
        );
    }
}
