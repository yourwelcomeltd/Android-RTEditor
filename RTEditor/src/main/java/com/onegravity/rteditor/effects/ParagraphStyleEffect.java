package com.onegravity.rteditor.effects;

import android.graphics.Paint;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.LineHeightSpan;

import com.onegravity.rteditor.RTEditText;
import com.onegravity.rteditor.spans.ParagraphStyle;
import com.onegravity.rteditor.spans.ParagraphStyleSpan;
import com.onegravity.rteditor.spans.RTSpan;
import com.onegravity.rteditor.utils.Helper;
import com.onegravity.rteditor.utils.Paragraph;
import com.onegravity.rteditor.utils.Selection;

import java.util.List;

/**
 * Created by Rew on 23/06/2016.
 */
public class ParagraphStyleEffect extends ParagraphEffect<ParagraphStyle, ParagraphStyleSpan>
{
    private ParagraphSpanProcessor<ParagraphStyle> mSpans2Process = new ParagraphSpanProcessor();

    @Override
    public synchronized void applyToSelection(RTEditText editor, Selection selectedParagraphs, ParagraphStyle style) {
        final Spannable str = editor.getText();

        mSpans2Process.clear();

        for (Paragraph paragraph : editor.getParagraphs()) {
            // find existing ParagraphStyleSpan and add them to mSpans2Process to be removed
            List<RTSpan<ParagraphStyle>> existingSpans = getSpans(str, paragraph, SpanCollectMode.SPAN_FLAGS);
            mSpans2Process.removeSpans(existingSpans, paragraph);

            boolean isSelected = paragraph.isSelected(selectedParagraphs);

            ParagraphStyle styleToApply = style;
            if(styleToApply == null || isSelected == false)
            {
                if (existingSpans.size() > 0)
                {
                    RTSpan<ParagraphStyle> firstStyle = existingSpans.get(0);
                    styleToApply = firstStyle.getValue();
                }
                else
                {
                    styleToApply = ParagraphStyle.P;
                }
            }

            ParagraphStyleSpan paragraphStyleSpan = new ParagraphStyleSpan(styleToApply);
            mSpans2Process.addSpan(paragraphStyleSpan, paragraph);

            // Remove any existing paragraph style
            Effects.PARAGRAPH_STYLE.findSpans2Remove(str, paragraph, mSpans2Process);
        }

        // add or remove spans
        mSpans2Process.process(str);
    }
}
