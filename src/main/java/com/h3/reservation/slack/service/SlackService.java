package com.h3.reservation.slack.service;

import com.h3.reservation.slack.InitMenuType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.dto.response.factory.InitResponseFactory;
import com.h3.reservation.slack.dto.response.factory.RetrieveResponseFactory;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;
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

    private static final String TOKEN = System.getenv("BOT_TOKEN");
    private static final String AUTHORIZATION = "Bearer " + TOKEN;

    public String verify(VerificationRequest dto) {
        return dto.getChallenge();
    }

    public void eventCallBack(EventCallbackRequest dto) {
        String postUrl = "https://slack.com/api/chat.postMessage";
        send(postUrl, InitResponseFactory.of(dto.getChannel()));
    }

    public void viewModal(BlockActionRequest dto) {
        InitMenuType type = InitMenuType.valueOf(dto.getAction_id().toUpperCase());
        String postUrl = "https://slack.com/api/views.open";
        Object response = new PlainText(dto.getAction_id());
        if (InitMenuType.RETRIEVE.equals(type)) {
            response = RetrieveResponseFactory.of(dto.getTrigger_id());
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

    public ModalUpdateResponse updateModal() {
        return new ModalUpdateResponse(
            new ModalView(
                new PlainText("조회하기"),
                new PlainText("확인"),
                generateDummyBlocks()
            )
        );
    }

    private List<Block> generateDummyBlocks() {
        return Arrays.asList(
            new SectionBlock(
                new MrkdwnText("2019-12-05 12:10-14:10 회의실 예약 현황입니다.")
            ),
            new DividerBlock(),
            new ContextBlock(
                Collections.singletonList(
                    new MrkdwnText("*회의실 1*")
                )
            ),
            new DividerBlock(),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("버디"),
                    new PlainText("12:10-13:10")
                )
            ),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("희봉"),
                    new PlainText("13:10-14:10")
                )
            ),
            new DividerBlock(),
            new ContextBlock(
                Collections.singletonList(
                    new MrkdwnText("*회의실 2*")
                )
            ),
            new SectionBlock(
                new MrkdwnText("*스터디*"),
                Arrays.asList(
                    new PlainText("닉"),
                    new PlainText("12:10-13:10")
                )
            ),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("도넛"),
                    new PlainText("13:10-14:10")
                )
            )
        );
    }
}
