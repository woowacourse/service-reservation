package com.h3.reservation.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicParser {

    private BasicParser() {
    }

    public static List<String> parse(final String text, final String delimiter) {
        return Arrays.stream(text.split(delimiter))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
