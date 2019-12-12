package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                generateBlocks()
            )
        );
    }

    private static List<Block> generateBlocks() {
        List<Block> blocks = new ArrayList<>();
        addTitleBlock(blocks);
        addReserveBlocks(blocks);
        return blocks;
    }

    private static void addTitleBlock(List<Block> blocks) {
        blocks.add(new SectionBlock(new PlainText(":tada: 예약이 완료되었습니다!")));
        blocks.add(new DividerBlock());
    }

    private static void addReserveBlocks(List<Block> blocks) {
        blocks.add(new SectionBlock(
            new MrkdwnText("*" + "프로젝트" + "*"),
            Arrays.asList(
                new PlainText("버디"),
                new PlainText("2019-12-12 15:00-16:00")
            )
        ));
    }
}
