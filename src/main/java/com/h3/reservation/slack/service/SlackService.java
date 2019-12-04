package com.h3.reservation.slack.service;

import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.Block;
import com.h3.reservation.slack.dto.response.DatePicker;
import com.h3.reservation.slack.dto.response.Element;
import com.h3.reservation.slack.dto.response.EventCallbackResponse;
import com.h3.reservation.slack.dto.response.ModalView;
import com.h3.reservation.slack.dto.response.RetrieveBlock;
import com.h3.reservation.slack.dto.response.RetrieveResponse;
import com.h3.reservation.slack.dto.response.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

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
        String postUrl = "https://slack.com/api/views.open";

        send(postUrl, generateRetrieveResponse(dto.getTrigger_id()));
    }

    private RetrieveResponse generateRetrieveResponse(String trigger_id) {
        DatePicker datePicker = new DatePicker("datepicker", "2019-12-04", new Text("날짜를 선택하세요"));
        ModalView modalView = new ModalView("modal", new Text("예약 조회하기"), new Text("조회"), new Text("취소"),
            Arrays.asList(new RetrieveBlock("input", datePicker, new Text("조회할 날짜를 선택하세요"))));
        return new RetrieveResponse(trigger_id, modalView);
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
        logger.error("webclient response 응답 : {}", response);

    }

    private EventCallbackResponse generateInitResponse(String channel) {
        List<Element> elements = Arrays.asList(
            Element.textButton(":spiral_calendar_pad: 전체 조회", "retrieve"),
            Element.textButton(":pushpin: 회의실 예약", "reserve"),
            Element.textButton(":file_folder: 예약 확인", "confirm"),
            Element.textButton(":memo: 예약 변경", "change"),
            Element.textButton(":scissors: 예약 취소", "cancel"));

        List<Block> blocks = Collections.singletonList(new Block("actions", elements));

        return new EventCallbackResponse(channel, blocks);
    }
}
