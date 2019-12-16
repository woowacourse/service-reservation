package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class DatepickerBlock {
    class Datepicker {
        private String selectedDate;

        public Datepicker() {
        }

        public Datepicker(String selectedDate) {
            this.selectedDate = selectedDate;
        }

        public String getSelectedDate() {
            return selectedDate;
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

    public String getSelectedDate() {
        return datepicker.getSelectedDate();
    }
}
