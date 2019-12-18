package com.h3.reservation.slack;

import com.h3.reservation.slack.dto.response.changeandcancel.ChangeAndCancelStartResponseFactory;
import com.h3.reservation.slack.dto.response.reserve.ReserveStartResponseFactory;
import com.h3.reservation.slack.dto.response.retrieve.RetrieveStartResponseFactory;

import java.util.function.Function;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public enum InitMenuType {
    RETRIEVE(RetrieveStartResponseFactory::of),
    RESERVE(ReserveStartResponseFactory::of),
    CHANGE(ChangeAndCancelStartResponseFactory::of);

    private Function<String, Object> function;

    public static InitMenuType of(String name) {
        return valueOf(name.toUpperCase());
    }

    InitMenuType(Function<String, Object> function) {
        this.function = function;
    }

    public Object apply(String id) {
        return function.apply(id);
    }
}
