package com.onegravity.rteditor.toolbar.spinner;

import com.onegravity.rteditor.fonts.RTTypeface;
import com.onegravity.rteditor.spans.ParagraphStyle;

/**
 * Created by Rew on 23/06/2016.
 */
public class ParagraphStyleSpinnerItem extends SpinnerItem {
    final private ParagraphStyle paragraphStyle;

    public ParagraphStyleSpinnerItem(ParagraphStyle style) {
        super(style.name());
        this.paragraphStyle = style;
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }
}