package com.h3.reservation.slack.dto.response.changeandcancel;

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

public class CancelFinishedResponseFactory {
    public static ModalUpdateResponse of(Reservation reservation) {
        return new ModalUpdateResponse(
            new ModalView(
                "cancel_finished",
                new PlainText("취소하기"),
                new PlainText("확인"),
                generateBlocks(reservation),
                true
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
        blocks.add(new SectionBlock(new PlainText("취소되었습니다.")));
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
