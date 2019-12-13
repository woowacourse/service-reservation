package com.h3.reservation.slack.fragment.element;

public class ImageElement extends Element {
    private String imageUrl;
    private String altText;

    public ImageElement() {
        super(ElementType.IMAGE);
    }

    public ImageElement(String imageUrl, String altText) {
        super(ElementType.IMAGE);
        this.imageUrl = imageUrl;
        this.altText = altText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAltText() {
        return altText;
    }
}
