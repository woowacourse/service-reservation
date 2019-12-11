package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

import com.h3.reservation.slack.fragment.composition.Option;

public class EndTime {
    private Option selectedOption;

    public EndTime() {
    }

    public EndTime(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }
}
