package com.h3.reservation.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicParserTest {

    @Test
    void parse() {
        String summary = "회의실1 / 버디 / 프로젝트";

        assertEquals(BasicParser.parse(summary, "/"), Arrays.asList("회의실1", "버디", "프로젝트"));
    }
}