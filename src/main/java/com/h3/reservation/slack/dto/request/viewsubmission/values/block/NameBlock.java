package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class NameBlock {
    class Name {
        private String value;

        public Name() {
        }

        public Name(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private Name name;

    public NameBlock() {
    }

    public NameBlock(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public String getValue() {
        return name.getValue();
    }
}
