package com.h3.reservation.calendar.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SummaryParserTest {

    @Test
    void parse() {
        List<String> tokens = SummaryParser.parse("회의실1 / 버디 / 스터디");

        assertThat(tokens).isEqualTo(Arrays.asList("회의실1", "버디", "스터디"));
    }
}