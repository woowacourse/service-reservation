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
    private static final String HOUR = "시";
    private static final String MINUTE = "분";
    private static final String SUFFIX_HOUR_BLOCK = "_hour_block";
    private static final String SUFFIX_HOUR = "_hour";
    private static final String SUFFIX_MINUTE_BLOCK = "_minute_block";
    private static final String SUFFIX_MINUTE = "_minute";
    private static final int MIN_HOUR = 10;
    private static final int MAX_HOUR = 21;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 50;
    private static final int MINUTE_INTERVAL = 10;

    public static ActionsBlock generateHourPicker(String blockId, String hourActionId, String minuteActionId,
                                                  int initialHour, int initialMinute) {
        return new ActionsBlock(
            blockId,
            Arrays.asList(
                new StaticSelectElement(
                    new PlainText(HOUR),
                    hourActionId,
                    new Option(new PlainText(initialHour + HOUR), String.valueOf(initialHour)),
                    generateHourSelect()
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

    public static InputBlock generateHourPickerWithInitValue(String prefix, int initialHour) {
        return new InputBlock(
            prefix + SUFFIX_HOUR_BLOCK,
            new PlainText("시간을 선택하세요."),
            new StaticSelectElement(
                new PlainText(HOUR),
                prefix + SUFFIX_HOUR,
                new Option(new PlainText(initialHour + HOUR), String.valueOf(initialHour)),
                generateHourSelect()
            )
        );
    }

    public static InputBlock generateHourPicker(String prefix) {
        return new InputBlock(
            prefix + SUFFIX_HOUR_BLOCK,
            new PlainText("시간을 선택하세요."),
            new StaticSelectElement(
                new PlainText(HOUR),
                prefix + SUFFIX_HOUR,
                generateHourSelect()
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

    private static List<Option> generateHourSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
            options.add(new Option(new PlainText(i + HOUR), String.valueOf(i)));
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
