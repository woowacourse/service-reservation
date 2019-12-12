package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class DescriptionBlock {
    class Description {
        private String value;

        public Description() {
        }

        public Description(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private Description description;

    public DescriptionBlock() {
    }

    public DescriptionBlock(Description description) {
        this.description = description;
    }

    public Description getDescription() {
        return description;
    }

    public String getValue() {
        return description.getValue();
    }
}
