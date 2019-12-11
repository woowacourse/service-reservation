package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

import com.h3.reservation.slack.fragment.composition.Option;

public class StartTime {
    private Option selectedOption;

    public StartTime() {
    }

    public StartTime(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }
}
