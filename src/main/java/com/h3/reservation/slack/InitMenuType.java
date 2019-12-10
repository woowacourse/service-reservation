package com.h3.reservation.slack;

import com.h3.reservation.slack.dto.response.factory.ChangeResponseFactory;
import com.h3.reservation.slack.dto.response.factory.ReserveResponseFactory;
import com.h3.reservation.slack.dto.response.factory.RetrieveResponseFactory;

import java.util.function.Function;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public enum InitMenuType {
    RETRIEVE(RetrieveResponseFactory::of),
    RESERVE(ReserveResponseFactory::of),
    CHANGE(ChangeResponseFactory::of);

    private Function<String, Object> function;

    InitMenuType(Function<String, Object> function) {
        this.function = function;
    }

    public Object apply(String id) {
        return function.apply(id);
    }
}
