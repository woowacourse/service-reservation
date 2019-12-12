package com.h3.reservation.calendar.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryParser {

//    @Value("${calendar.summary.delimiter:/}")
    private static String summaryDelimiter = "/";

    private SummaryParser() {
    }

    public static List<String> parse(String summary) {
        return Arrays.stream(summary.split(summaryDelimiter))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
