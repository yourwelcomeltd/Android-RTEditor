package com.onegravity.rteditor.toolbar.spinner;

import com.onegravity.rteditor.fonts.RTTypeface;
import com.onegravity.rteditor.spans.ParagraphStyle;

/**
 * Created by Rew on 23/06/2016.
 */
public class ParagraphStyleSpinnerItem extends SpinnerItem {
    final private ParagraphStyle paragraphStyle;

    public ParagraphStyleSpinnerItem(ParagraphStyle style) {
        super(getDisplayName(style));
        this.paragraphStyle = style;
    }

    public static String getDisplayName(ParagraphStyle style)
    {
        switch(style)
        {
            case P:
                return "Paragraph";

            case H1:
                return "Header 1";

            case H2:
                return "Header 2";

            case H3:
                return "Header 3";

            case H4:
                return "Header 4";

            case H5:
                return "Header 5";

            case H6:
                return "Header 6";

            default:
                return "";
        }
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }
}