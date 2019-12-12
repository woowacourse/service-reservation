package com.h3.reservation.slack.fragment.block;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.fragment.composition.text.Text;
import com.h3.reservation.slack.fragment.element.Element;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionBlock extends Block {
    private Text text;
    private List<Text> fields;
    private Element accessory;

    public SectionBlock() {
        super(BlockType.SECTION);
    }

    public SectionBlock(Text text) {
        super(BlockType.SECTION);
        this.text = text;
    }

    public SectionBlock(Text text, List<Text> fields) {
        super(BlockType.SECTION);
        this.text = text;
        this.fields = fields;
    }

    public SectionBlock(Text text, Element accessory) {
        super(BlockType.SECTION);
        this.text = text;
        this.accessory = accessory;
    }

    public Text getText() {
        return text;
    }

    public List<Text> getFields() {
        return fields;
    }

    public Element getAccessory() {
        return accessory;
    }
}
