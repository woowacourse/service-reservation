package com.h3.reservation.slack.fragment.error;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-24
 */
public class DescriptionErrors extends Errors {
    private String descriptionBlock;

    public DescriptionErrors() {
    }

    public DescriptionErrors(String descriptionBlock) {
        this.descriptionBlock = descriptionBlock;
    }

    public String getDescriptionBlock() {
        return descriptionBlock;
    }
}
