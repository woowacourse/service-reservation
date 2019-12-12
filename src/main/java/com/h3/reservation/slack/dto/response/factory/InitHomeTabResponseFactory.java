package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.InitialHomeTabResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.element.Element;
import com.h3.reservation.slack.fragment.view.HomeView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InitHomeTabResponseFactory {
    public static InitialHomeTabResponse of(String userId) {
        List<Element> elements = Arrays.asList(
            new ButtonElement(new PlainText(":spiral_calendar_pad: 전체 조회"), "retrieve"),
            new ButtonElement(new PlainText(":pushpin: 회의실 예약"), "reserve"),
            new ButtonElement(new PlainText(":scissors: 예약 변경/취소"), "change"));

        List<Block> blocks = Collections.singletonList(new ActionsBlock("initial_block", elements));

        return new InitialHomeTabResponse(userId, new HomeView(blocks));
    }
}
