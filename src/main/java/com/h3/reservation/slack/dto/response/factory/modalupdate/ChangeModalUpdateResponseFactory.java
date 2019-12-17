package com.h3.reservation.slack.dto.response.factory.modalupdate;

import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.view.ModalView;
import com.h3.reservation.slackcalendar.domain.Reservation;
import com.h3.reservation.slackcalendar.domain.Reservations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class ChangeModalUpdateResponseFactory {
    public static ModalUpdateResponse of(Reservations reservations) {
        return new ModalUpdateResponse(
            new ModalView(
                "change_result",
                new PlainText("변경/취소하기"),
                new PlainText("확인"),
                generateBlocks2(reservations)
            )
        );
    }

    private static List<Block> generateBlocks2(Reservations reservations) {
        List<Block> blocks = new ArrayList<>();
        addRetrieveBlocks(reservations, blocks);
        return blocks;
    }

    private static void addRetrieveBlocks(Reservations reservations, List<Block> blocks) {
        if (reservations.isEventEmpty()) {
            addEmptyBlock(blocks);
            return;
        }
        addReservationBlocks(reservations, blocks);
    }

    private static void addEmptyBlock(List<Block> blocks) {
        blocks.addAll(Collections.singletonList(
            new SectionBlock(
                new PlainText("예약이 없습니다!")
            )
        ));
    }

    private static void addReservationBlocks(Reservations reservations, List<Block> blocks) {
        TreeMap<MeetingRoom, List<Reservation>> roomReservation = reservations.stream()
            .collect(groupingBy(Reservation::getRoom, TreeMap::new, Collectors.toList()));
        addReservationBlocksByRoom(blocks, roomReservation);
    }

    private static void addReservationBlocksByRoom(List<Block> blocks, TreeMap<MeetingRoom, List<Reservation>> roomReservation) {
        roomReservation.forEach((key, value) -> blocks.addAll(generateReservations(key.getName(), value)));
    }

    private static List<Block> generateReservations(String room, List<Reservation> reservations) {
        List<Block> blocks = new ArrayList<>();
        addRoomBlock(room, blocks);
        reservations.forEach(event -> addReservationBlock(blocks, event));
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

    private static void addReservationBlock(List<Block> blocks, Reservation reservation) {
        blocks.add(generateReservation(reservation.getBooker(), reservation.getDescription(),
            reservation.getFormattedDate(), reservation.getFormattedStartTime() + "-" + reservation.getFormattedEndTime()));
        blocks.add(generateChangeButton(reservation.getId()));
    }

    private static Block generateReservation(String booker, String purpose, String date, String time) {
        return new SectionBlock(
            new MrkdwnText("*" + purpose + "*\n" + date + " " + time + " - " + booker),
            Arrays.asList(
                new PlainText(booker),
                new PlainText(time)
            )
        );
    }

    private static ActionsBlock generateChangeButton(String id) {
        return new ActionsBlock(
            "change_block",
            Arrays.asList(
                new ButtonElement(new PlainText("변경"), "request_change_" + id, "primary"),
                new ButtonElement(new PlainText("취소"), "request_cancel_" + id, "danger")
            )
        );
    }

    private static List<Block> generateBlocks(Reservations reservations) {
        List<Block> blocks = new ArrayList<>();
        addRoomBlock("회의실 1", blocks);
        addReservationBlock(blocks);
        return blocks;
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
