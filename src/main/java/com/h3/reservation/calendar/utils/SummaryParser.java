package com.h3.reservation.calendar.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryParser {
    private SummaryParser() {
    }

    public static List<String> parse(String summary) {
        return Arrays.stream(summary.split("/"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
