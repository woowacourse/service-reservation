package com.h3.reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h3.reservation.slack.RequestType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.service.SlackService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@RestController
public class BotController {
    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    private static final String TYPE = "type";
    private static final String PAYLOAD = "payload";

    private final SlackService service;
    private final ObjectMapper objectMapper;

    public BotController(SlackService service) {
        this.service = service;
        this.objectMapper = initObjectMapper();
    }

//    @PostMapping("/slack/action")
//    public ResponseEntity<String> action(@RequestBody JSONObject reqJson) throws JsonProcessingException {
//        switch (RequestType.get(reqJson.getAsString(TYPE))) {
//            case URL_VERIFICATION:
//                return ResponseEntity.ok(service.verify(jsonToDto(reqJson, VerificationRequest.class)));
//            case EVENT_CALLBACK:
//                service.showMenu(jsonToDto(reqJson, EventCallbackRequest.class));
//                return ResponseEntity.ok().build();
//            default:
//                return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @PostMapping(value = "/slack/interaction", consumes = {"application/x-www-form-urlencoded"})
//    public ResponseEntity<ModalUpdateResponse> interaction(@RequestParam Map<String, String> req)
//            throws IOException, ParseException {
//        logger.debug("req : {}", req.toString());
//        JSONObject reqJson = fetchJsonFromRequest(req);
//        switch (RequestType.get(reqJson.getAsString(TYPE))) {
//            case BLOCK_ACTIONS:
//                service.showModal(jsonToDto(reqJson, BlockActionRequest.class));
//                return ResponseEntity.ok().build();
//            case VIEW_SUBMISSION:
//                return ResponseEntity.ok(service.updateModal());
//            default:
//                return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping("/slack/action")
    public ResponseEntity<String> _action(@RequestBody JsonNode reqJson) throws JsonProcessingException {
        switch (RequestType.get(reqJson.get(TYPE).asText())) {
            case URL_VERIFICATION:
                return ResponseEntity.ok(service.verify(objectMapper.treeToValue(reqJson, VerificationRequest.class)));
            case EVENT_CALLBACK:
                service.showMenu(objectMapper.treeToValue(reqJson, EventCallbackRequest.class));
                return ResponseEntity.ok().build();
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/slack/interaction", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<ModalUpdateResponse> _interaction(@RequestBody JsonNode req) throws IOException {
        JsonNode reqJson = req.get(PAYLOAD);
        switch (RequestType.get(reqJson.get(TYPE).asText())) {
            case BLOCK_ACTIONS:
                service.showModal(objectMapper.treeToValue(reqJson, BlockActionRequest.class));
                return ResponseEntity.ok().build();
            case VIEW_SUBMISSION:
                return ResponseEntity.ok(service.updateModal());
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private <T> T jsonToDto(JSONObject object, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(object.toString(), type);
    }

    private JSONObject fetchJsonFromRequest(Map<String, String> req) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(req.get(PAYLOAD));
        return (JSONObject) obj;
    }
}
