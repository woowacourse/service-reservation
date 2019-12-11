package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

import com.h3.reservation.slack.fragment.composition.Option;

public class StartMinute {
    private Option selectedOption;

    public StartMinute() {
    }

    public StartMinute(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }
}
