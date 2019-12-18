package com.h3.reservation.slack.dto.response.reserve;

import com.h3.reservation.slack.dto.response.common.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;
import com.h3.reservation.slackcalendar.domain.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class ReserveFinishedResponseFactory {
    public static ModalUpdateResponse of(Reservation reserve) {
        return new ModalUpdateResponse(
            new ModalView(
                "reserve_finished",
                new PlainText("예약하기"),
                new PlainText("확인"),
                generateBlocks(reserve)
            )
        );
    }

    private static List<Block> generateBlocks(Reservation reservation) {
        List<Block> blocks = new ArrayList<>();
        addTitleBlock(blocks);
        addReserveBlocks(blocks, reservation);
        return blocks;
    }

    private static void addTitleBlock(List<Block> blocks) {
        blocks.add(new SectionBlock(new PlainText(":tada: 예약이 완료되었습니다!")));
        blocks.add(new DividerBlock());
    }

    private static void addReserveBlocks(List<Block> blocks, Reservation reservation) {
        blocks.add(generateReserve(reservation.getDescription(), reservation.getBooker(), reservation.getRoom().getName()
            , reservation.getFormattedDate(), reservation.getFormattedStartTime(), reservation.getFormattedEndTime()));
    }

    private static SectionBlock generateReserve(String description, String booker, String room,
                                                String date, String startTime, String endTime) {
        return new SectionBlock(
            new MrkdwnText("*" + room + " / " + description + "*"),
            Arrays.asList(
                new PlainText(booker),
                new PlainText(date + " " + startTime + "-" + endTime)
            )
        );
    }
}
