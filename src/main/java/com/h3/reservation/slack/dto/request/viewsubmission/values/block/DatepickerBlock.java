package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class DatepickerBlock {
    class Datepicker {
        private String selected_date;

        public Datepicker() {
        }

        public Datepicker(String selected_date) {
            this.selected_date = selected_date;
        }

        public String getSelected_date() {
            return selected_date;
        }
    }

    private Datepicker datepicker;

    public DatepickerBlock() {
    }

    public DatepickerBlock(Datepicker datepicker) {
        this.datepicker = datepicker;
    }

    public Datepicker getDatepicker() {
        return datepicker;
    }

    public String getSelected_date() {
        return datepicker.getSelected_date();
    }
}
