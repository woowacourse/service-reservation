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
    public static ActionsBlock generateTimePicker(String blockId, String timeActionId, String minuteActionId,
                                                   int initialTime, int initialMinute) {
        return new ActionsBlock(
            blockId,
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

    public static InputBlock generateTimePicker(String blockId, String timeActionId, int initialTime) {
        return new InputBlock(
            blockId,
            new PlainText("시간을 선택하세요."),
            new StaticSelectElement(
                new PlainText("시"),
                timeActionId,
                new Option(new PlainText(initialTime + "시"), String.valueOf(initialTime)),
                generateTimeSelect()
            )
        );
    }

    public static InputBlock generateMinutePicker(String blockId, String minuteActionId, int initialMinute) {
        return new InputBlock(
            blockId,
            new PlainText("분을 선택하세요."),
            new StaticSelectElement(
                new PlainText("분"),
                minuteActionId,
                new Option(new PlainText(initialMinute + "분"), String.valueOf(initialMinute)),
                generateMinuteSelect()
            )
        );
    }

    private static List<Option> generateTimeSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 10; i <= 21; i++) {
            options.add(new Option(new PlainText(i + "시"), String.valueOf(i)));
        }
        return options;
    }

    private static List<Option> generateMinuteSelect() {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i <= 50; i += 10) {
            options.add(new Option(new PlainText(i + "분"), String.valueOf(i)));
        }
        return options;
    }
}
