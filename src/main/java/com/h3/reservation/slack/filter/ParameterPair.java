package com.h3.reservation.slack.filter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

class ParameterPair implements Map.Entry<String, String[]> {
    private final String key;
    private String[] value;

    public ParameterPair(String key, String[] value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String[] getValue() {
        return value;
    }

    @Override
    public String[] setValue(String[] value) {
        this.value = value;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterPair)) {
            return false;
        }
        final ParameterPair rhs = (ParameterPair) o;
        return Objects.equals(key, rhs.key) && Arrays.equals(value, rhs.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(key);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}