package com.h3.reservation.slack.dto.response.factory;

import com.h3.reservation.slack.fragment.block.ActionsBlock;
import com.h3.reservation.slack.fragment.block.InputBlock;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.element.StaticSelectElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonResponseFactory {
    private static final String TIME = "시";
    private static final String MINUTE = "분";
    private static final String SUFFIX_TIME_BLOCK = "_time_block";
    private static final String SUFFIX_TIME = "_time";
    private static final String SUFFIX_MINUTE_BLOCK = "_minute_block";
    private static final String SUFFIX_MINUTE = "_minute";
    private static final int MIN_TIME = 10;
    private static final int MAX_TIME = 21;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 50;
    private static final int MINUTE_INTERVAL = 10;

    public static ActionsBlock generateTimePicker(String blockId, String timeActionId, String minuteActionId,
                                                  int initialTime, int initialMinute) {
        return new ActionsBlock(
            blockId,
            Arrays.asList(
                new StaticSelectElement(
                    new PlainText(TIME),
                    timeActionId,
                    new Option(new PlainText(initialTime + TIME), String.valueOf(initialTime)),
                    generateTimeSelect()
                ),
                new StaticSelectElement(
                    new PlainText(MINUTE),
                    minuteActionId,
                    new Option(new PlainText(initialMinute + MINUTE), String.valueOf(initialMinute)),
                    generateMinuteSelect()
                )
            )
        );
    }

    public static InputBlock generateTimePickerWithInitValue(String prefix, int initialTime) {
        return new InputBlock(
            prefix + SUFFIX_TIME_BLOCK,
            new PlainText("시간을 선택하세요."),
            new StaticSelectElement(
                new PlainText(TIME),
                prefix + SUFFIX_TIME,
                new Option(new PlainText(initialTime + TIME), String.valueOf(initialTime)),
                generateTimeSelect()
            )
        );
    }

    public static InputBlock generateTimePicker(String prefix) {
        return new InputBlock(
            prefix + SUFFIX_TIME_BLOCK,
            new PlainText("시간을 선택하세요."),
            new StaticSelectElement(
                new PlainText(TIME),
                prefix + SUFFIX_TIME,
                generateTimeSelect()
            )
        );
    }

    public static InputBlock generateMinutePickerWithInitValue(String prefix, int initialMinute) {
        return new InputBlock(
            prefix + SUFFIX_MINUTE_BLOCK,
            new PlainText("분을 선택하세요."),
            new StaticSelectElement(
                new PlainText(MINUTE),
                prefix + SUFFIX_MINUTE,
                new Option(new PlainText(initialMinute + MINUTE), String.valueOf(initialMinute)),
                generateMinuteSelect()
            )
        );
    }

    public static InputBlock generateMinutePicker(String prefix) {
        return new InputBlock(
            prefix + SUFFIX_MINUTE_BLOCK,
            new PlainText("분을 선택하세요."),
            new StaticSelectElement(
                new PlainText(MINUTE),
                prefix + SUFFIX_MINUTE,
                generateMinuteSelect()
            )
        );
    }

    private static List<Option> generateTimeSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = MIN_TIME; i <= MAX_TIME; i++) {
            options.add(new Option(new PlainText(i + TIME), String.valueOf(i)));
        }
        return options;
    }

    private static List<Option> generateMinuteSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = MIN_MINUTE; i <= MAX_MINUTE; i += MINUTE_INTERVAL) {
            options.add(new Option(new PlainText(i + MINUTE), String.valueOf(i)));
        }
        return options;
    }
}
