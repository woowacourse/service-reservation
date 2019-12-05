package com.h3.reservation.slack.fragment.block;


import com.h3.reservation.slack.fragment.composition.text.Text;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class SectionBlock extends Block {
    private Text text;

    public SectionBlock() {
        super(BlockType.SECTION);
    }

    public SectionBlock(Text text) {
        super(BlockType.SECTION);
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
