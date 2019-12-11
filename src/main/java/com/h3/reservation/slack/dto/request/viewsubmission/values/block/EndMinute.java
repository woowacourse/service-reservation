package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

import com.h3.reservation.slack.fragment.composition.Option;

public class EndMinute {
    private Option selectedOption;

    public EndMinute() {
    }

    public EndMinute(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }
}
