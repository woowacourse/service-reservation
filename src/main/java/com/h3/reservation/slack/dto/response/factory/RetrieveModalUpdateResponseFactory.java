package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.RetrieveModalUpdateResponse;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;
import com.h3.reservation.slackcalendar.dto.SlackCalendarEvent;
import com.h3.reservation.slackcalendar.dto.SlackCalendarRetrieveResponse;

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
 * @date 2019-12-10
 */
public class RetrieveModalUpdateResponseFactory {
    public static RetrieveModalUpdateResponse of(SlackCalendarRetrieveResponse response) {
        return new RetrieveModalUpdateResponse(
            new ModalView(
                new PlainText("조회하기"),
                new PlainText("확인"),
                generateBlocks(response)
            )
        );
    }

    private static List<Block> generateBlocks(SlackCalendarRetrieveResponse response) {
        List<Block> blocks = new ArrayList<>();
        addTitleBlock(response, blocks);
        addRetrieveBlocks(response, blocks);
        return blocks;
    }

    private static void addTitleBlock(SlackCalendarRetrieveResponse response, List<Block> blocks) {
        blocks.addAll(generateTitle(response.getDate(), response.getStartTime(), response.getEndTime()));
    }

    private static List<Block> generateTitle(String date, String startTime, String endTime) {
        return Collections.singletonList(
            new SectionBlock(
                new MrkdwnText(date + " " + startTime + "-" + endTime + " 회의실 예약 현황입니다.")
            )
        );
    }

    private static void addRetrieveBlocks(SlackCalendarRetrieveResponse response, List<Block> blocks) {
        if (response.isEventEmpty()) {
            addEmptyBlock(blocks);
            return;
        }
        addReservationBlocks(response, blocks);
    }

    private static void addEmptyBlock(List<Block> blocks) {
        blocks.addAll(Collections.singletonList(
            new SectionBlock(
                new PlainText("예약이 없습니다!")
            )
        ));
    }

    private static void addReservationBlocks(SlackCalendarRetrieveResponse response, List<Block> blocks) {
        TreeMap<String, List<SlackCalendarEvent>> roomReservation = response.getSlackCalendarEvents().stream()
            .collect(groupingBy(SlackCalendarEvent::getRoom, TreeMap::new, Collectors.toList()));
        addReservationBlocksByRoom(blocks, roomReservation);
    }

    private static void addReservationBlocksByRoom(List<Block> blocks, TreeMap<String, List<SlackCalendarEvent>> roomReservation) {
        roomReservation.forEach((key, value) -> blocks.addAll(generateReservations(key, value)));
    }

    private static List<Block> generateReservations(String room, List<SlackCalendarEvent> events) {
        List<Block> blocks = new ArrayList<>();
        addRoomBlock(room, blocks);
        events.forEach(event -> addReservationBlock(blocks, event));
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

    private static void addReservationBlock(List<Block> blocks, SlackCalendarEvent event) {
        blocks.add(generateReservation(event.getBooker(), event.getPurpose(), event.getStartTime() + "-" + event.getEndTime()));
    }

    private static Block generateReservation(String booker, String purpose, String time) {
        return new SectionBlock(
            new MrkdwnText("*" + purpose + "*"),
            Arrays.asList(
                new PlainText(booker),
                new PlainText(time)
            )
        );
    }
}
