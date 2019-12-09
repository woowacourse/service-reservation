package com.h3.reservation.slack.fragment.block;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.fragment.composition.text.Text;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionBlock extends Block {
    private String block_id;
    private Text text;
    private List<Text> fields;

    public SectionBlock() {
        super(BlockType.SECTION);
    }

    public SectionBlock(String block_id, Text text) {
        super(BlockType.SECTION);
        this.block_id = block_id;
        this.text = text;
    }

    public SectionBlock(String block_id, Text text, List<Text> fields) {
        super(BlockType.SECTION);
        this.block_id = block_id;
        this.text = text;
        this.fields = fields;
    }

    public Text getText() {
        return text;
    }

    public List<Text> getFields() {
        return fields;
    }
}
