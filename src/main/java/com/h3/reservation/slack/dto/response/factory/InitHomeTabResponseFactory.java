package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.dto.response.InitialHomeTabResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.element.Element;
import com.h3.reservation.slack.fragment.element.ImageElement;
import com.h3.reservation.slack.fragment.view.HomeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitHomeTabResponseFactory {
    public static InitialHomeTabResponse of(String userId) {
        List<Block> blocks = new ArrayList<>();

        blocks.add(new SectionBlock(
            new MrkdwnText("안녕하세요!\n우아한테크코스 캘린더 예약봇입니다.:santa:\n즐거운 연말 보내세요~ :wave:"),
            new ImageElement("https://api.slack.com/img/blocks/bkb_template_images/notifications.png",
                "calendar thumbnail")
        ));
        blocks.add(new DividerBlock());

        List<Element> elements = Arrays.asList(
            new ButtonElement(new PlainText(":spiral_calendar_pad: 전체 조회"), "retrieve"),
            new ButtonElement(new PlainText(":pushpin: 회의실 예약"), "reserve"),
            new ButtonElement(new PlainText(":scissors: 예약 변경/취소"), "change"));

        blocks.add(new ActionsBlock("initial_block", elements));

        return new InitialHomeTabResponse(userId, new HomeView(blocks));
    }
}
