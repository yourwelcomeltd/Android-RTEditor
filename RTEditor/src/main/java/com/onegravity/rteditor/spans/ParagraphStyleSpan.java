package com.onegravity.rteditor.spans;

import android.graphics.Paint;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LineHeightSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;

/**
 * Created by Rew on 23/06/2016.
 */
public class ParagraphStyleSpan extends MetricAffectingSpan implements RTSpan<ParagraphStyle>, RTParagraphSpan<ParagraphStyle>, android.text.style.ParagraphStyle, LineHeightSpan
{
    ParagraphStyle style;

    android.text.style.RelativeSizeSpan childSpan;

    private static final float P_SIZE = 1.0f;
    private static final float H1_SIZE = 1.5f;
    private static final float H2_SIZE = 1.4f;
    private static final float H3_SIZE = 1.3f;
    private static final float H4_SIZE = 1.2f;
    private static final float H5_SIZE = 1.1f;
    private static final float H6_SIZE = 1.0f;

    private static final float BOTTOM_MARGIN = 0.5f;

    float relativeSize;

    public ParagraphStyleSpan(ParagraphStyle style)
    {
        super();

        this.style = style;
        relativeSize = 1;
        switch(style)
        {
            case P:
                relativeSize = P_SIZE;
                break;

            case H1:
                relativeSize = H1_SIZE;
                break;

            case H2:
                relativeSize = H2_SIZE;
                break;

            case H3:
                relativeSize = H3_SIZE;
                break;

            case H4:
                relativeSize = H4_SIZE;
                break;

            case H5:
                relativeSize = H5_SIZE;
                break;

            case H6:
                relativeSize = H6_SIZE;
                break;

            default:
                break;
        }

        childSpan = new RelativeSizeSpan(relativeSize);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint)
    {
        childSpan.updateMeasureState(textPaint);
    }

    @Override
    public void updateDrawState(TextPaint textPaint)
    {
        childSpan.updateDrawState(textPaint);
    }

    @Override
    public RTParagraphSpan<ParagraphStyle> createClone()
    {
        return new ParagraphStyleSpan(style);
    }

    @Override
    public ParagraphStyle getValue()
    {
        return style;
    }

    @Override
    public void chooseHeight(CharSequence text, int start, int end, int spanStartV, int v, Paint.FontMetricsInt fontMetricsInt)
    {
        Spanned spanned = (Spanned) text;
        int st = spanned.getSpanStart(this);
        int en = spanned.getSpanEnd(this);
        /*if (start == st)
        {

        }*/
        // Add on some bottom margin for header styles
        if (end == en && style != ParagraphStyle.P)
        {
            float bottomMargin = BOTTOM_MARGIN * relativeSize * (fontMetricsInt.bottom - fontMetricsInt.top);
            fontMetricsInt.descent += bottomMargin;
            fontMetricsInt.bottom += bottomMargin;
        }
    }

}
