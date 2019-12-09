package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.InitialResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.element.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InitResponseFactory {
    public static InitialResponse of(String channel) {
        List<Element> elements = Arrays.asList(
            new ButtonElement(new PlainText(":spiral_calendar_pad: 전체 조회"), "retrieve"),
            new ButtonElement(new PlainText(":pushpin: 회의실 예약"), "reserve"),
            new ButtonElement(new PlainText(":file_folder: 예약 확인"), "confirm"),
            new ButtonElement(new PlainText(":memo: 예약 변경"), "change"),
            new ButtonElement(new PlainText(":scissors: 예약 취소"), "cancel"));

        List<ActionsBlock> blocks = Collections.singletonList(new ActionsBlock("initial_block", elements));

        return new InitialResponse(channel, blocks);
    }
}
