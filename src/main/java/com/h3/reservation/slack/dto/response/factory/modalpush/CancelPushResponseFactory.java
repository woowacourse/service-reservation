package com.h3.reservation.slack.dto.response.factory.modalpush;

import com.h3.reservation.slack.dto.response.ModalResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;
import com.h3.reservation.slackcalendar.domain.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CancelPushResponseFactory {
    public static ModalResponse of(String triggerId, Reservation reservation) {
        String time = reservation.getFormattedStartTime() + "-" + reservation.getFormattedEndTime();
        ModalView modalView = new ModalView(
            "cancel_request",
            reservation.getId(),
            new PlainText("취소하기"),
            new PlainText("네"),
            new PlainText("아니오"),
            generateBlocks(reservation)
        );
        return new ModalResponse(triggerId, modalView);
    }

    private static List<Block> generateBlocks(Reservation reservation) {
        List<Block> blocks = new ArrayList<>();
        addTitleBlock(blocks);
        addReserveBlocks(blocks, reservation);
        return blocks;
    }

    private static void addTitleBlock(List<Block> blocks) {
        blocks.add(new SectionBlock(new PlainText("아래 이벤트를 취소하시겠습니까?")));
        blocks.add(new DividerBlock());
    }

    private static void addReserveBlocks(List<Block> blocks, Reservation reservation) {
        blocks.add(generateReserve(reservation.getDescription(), reservation.getBooker(), reservation.getRoom().getName()
            , reservation.getFormattedDate(), reservation.getFormattedStartTime(), reservation.getFormattedEndTime()));
    }

    private static SectionBlock generateReserve(String description, String booker, String room, String date, String startTime, String endTime) {
        return new SectionBlock(
            new MrkdwnText("*" + room + " / " + description + "*"),
            Arrays.asList(
                new PlainText(booker),
                new PlainText(date + " " + startTime + "-" + endTime)
            )
        );
    }
}
