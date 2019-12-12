package com.h3.reservation.slack.dto.response.factory.modalupdate;

import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.OverflowElement;
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
                new MrkdwnText("*프로젝트*"),
                new OverflowElement(
                    "reservation_1",
                    Arrays.asList(
                        new Option(new PlainText(":pencil: 변경"), "change"),
                        new Option(new PlainText(":x: 취소"), "cancel")
                    )
                )
            )
        );
        blocks.add(
            new ContextBlock(
                Arrays.asList(
                    new PlainText("2019-12-12 12:00-13:00"),
                    new PlainText("버디")
                )
            )
        );
    }
}
