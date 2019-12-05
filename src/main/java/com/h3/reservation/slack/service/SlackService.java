package com.h3.reservation.slack.service;

import com.h3.reservation.slack.InitMenuType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.InitialResponse;
import com.h3.reservation.slack.dto.response.RetrieveResponse;
import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.ButtonElement;
import com.h3.reservation.slack.fragment.element.DatepickerElement;
import com.h3.reservation.slack.fragment.element.Element;
import com.h3.reservation.slack.fragment.element.StaticSelectElement;
import com.h3.reservation.slack.fragment.view.ModalView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@Service
public class SlackService {
    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private static final String TOKEN = "xoxb-628979079522-857825073798-rZkXu22vIyUyVIgTZeKjwKkJ";
    private static final String AUTHORIZATION = "Bearer " + TOKEN;

    public String verify(VerificationRequest dto) {
        return dto.getChallenge();
    }

    public void eventCallBack(EventCallbackRequest dto) {
        String postUrl = "https://slack.com/api/chat.postMessage";
        send(postUrl, generateInitResponse(dto.getChannel()));
    }

    public void viewModal(BlockActionRequest dto) {
        InitMenuType type = InitMenuType.valueOf(dto.getAction_id().toUpperCase());
        String postUrl = "https://slack.com/api/views.open";
        Object response = new PlainText(dto.getAction_id());
        if (InitMenuType.RETRIEVE.equals(type)) {
            response = generateRetrieveResponse(dto.getTrigger_id());
        }
        send(postUrl, response);
    }

    private void send(String url, Object dto) {
        WebClient webClient = WebClient
            .builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
            .defaultHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
            .build();

        String response = webClient.post()
            .body(BodyInserters.fromValue(dto))
            .exchange().block().bodyToMono(String.class)
            .block();
        logger.debug("webclient response 응답 : {}", response);
    }

    private InitialResponse generateInitResponse(String channel) {
        List<Element> elements = Arrays.asList(
            new ButtonElement(new PlainText(":spiral_calendar_pad: 전체 조회"), "retrieve"),
            new ButtonElement(new PlainText(":pushpin: 회의실 예약"), "reserve"),
            new ButtonElement(new PlainText(":file_folder: 예약 확인"), "confirm"),
            new ButtonElement(new PlainText(":memo: 예약 변경"), "change"),
            new ButtonElement(new PlainText(":scissors: 예약 취소"), "cancel"));

        List<ActionsBlock> blocks = Collections.singletonList(new ActionsBlock(elements));

        return new InitialResponse(channel, blocks);
    }

    private RetrieveResponse generateRetrieveResponse(String trigger_id) {
        DatepickerElement datePicker = new DatepickerElement("retrieve_datepicker");

        ModalView modalView = new ModalView(
            new PlainText("조회하기"),
            new PlainText("조회"),
            new PlainText("취소"),
            Arrays.asList(
                new InputBlock(new PlainText("조회할 날짜를 선택하세요."), datePicker),
                new SectionBlock(new MrkdwnText("*시작 시간을 선택하세요*")),
                generateTimePicker(
                    "retrieve_start_time", "retrieve_start_minute", 10, 0
                ),
                new SectionBlock(new MrkdwnText("*종료 시간을 선택하세요*")),
                generateTimePicker(
                    "retrieve_end_time", "retrieve_end_minute", 21, 50
                )
            )
        );
        return new RetrieveResponse(trigger_id, modalView);
    }

    private ActionsBlock generateTimePicker(String timeActionId, String minuteActionId,
                                            int initialTime, int initialMinute) {
        return new ActionsBlock(
            Arrays.asList(
                new StaticSelectElement(
                    new PlainText("시"),
                    timeActionId,
                    new Option(new PlainText(initialTime + "시"), String.valueOf(initialTime)),
                    generateTimeSelect()
                ),
                new StaticSelectElement(
                    new PlainText("분"),
                    minuteActionId,
                    new Option(new PlainText(initialMinute + "분"), String.valueOf(initialMinute)),
                    generateMinuteSelect()
                )
            )
        );
    }

    private List<Option> generateTimeSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 10; i <= 21; i++) {
            options.add(new Option(new PlainText(i + "시"), String.valueOf(i)));
        }
        return options;
    }

    private List<Option> generateMinuteSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i <= 50; i += 10) {
            options.add(new Option(new PlainText(i + "분"), String.valueOf(i)));
        }
        return options;
    }
}
