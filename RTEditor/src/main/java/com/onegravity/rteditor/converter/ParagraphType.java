/*
 * Copyright (C) 2015-2016 Emanuel Moecklin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onegravity.rteditor.converter;

import android.text.Layout;
import android.text.style.ParagraphStyle;

import com.onegravity.rteditor.spans.AlignmentSpan;
import com.onegravity.rteditor.spans.BulletSpan;
import com.onegravity.rteditor.spans.IndentationSpan;
import com.onegravity.rteditor.spans.NumberSpan;
import com.onegravity.rteditor.spans.ParagraphStyleSpan;

/*
 * This is a helper class for converting from Spanned to HTML and back.
 */
public enum ParagraphType {
    NONE("", "", "", "", false, false, false),
    ALIGNMENT_LEFT("<div align=\"left\">", "</div>", "", "", true, true, false),
    ALIGNMENT_CENTER("<div align=\"center\">", "</div>", "", "", true, true, false),
    ALIGNMENT_RIGHT("<div align=\"right\">", "</div>", "", "", true, true, false),
    BULLET("<ul>", "</ul>", "<li>", "</li>", false, true, false),
    NUMBERING("<ol>", "</ol>", "<li>", "</li>", false, true, false),
    INDENTATION_UL("<ul style='list-style-type:none;'>", "</ul>", "<li style='list-style-type:none;'>", "</li>", false, true, false),
    INDENTATION_OL("<ol style='list-style-type:none;'>", "</ol>", "<li style='list-style-type:none;'>", "</li>", false, true, false),
    P("<p>", "</p>", "", "", false, true, true),
    H1("<h1>", "</h1>", "", "", false, true, true),
    H2("<h2>", "</h2>", "", "", false, true, true),
    H3("<h3>", "</h3>", "", "", false, true, true),
    H4("<h4>", "</h4>", "", "", false, true, true),
    H5("<h5>", "</h5>", "", "", false, true, true),
    H6("<h6>", "</h6>", "", "", false, true, true);


    public static ParagraphType getInstance(ParagraphStyle style) {
        if (style instanceof AlignmentSpan)
        {
            Layout.Alignment align = ((AlignmentSpan) style).getValue();
            return align == Layout.Alignment.ALIGN_NORMAL ? ParagraphType.ALIGNMENT_LEFT : align == Layout.Alignment.ALIGN_CENTER ? ParagraphType.ALIGNMENT_CENTER : ParagraphType.ALIGNMENT_RIGHT;
        }
        else if(style instanceof com.onegravity.rteditor.spans.ParagraphStyleSpan)
        {
            com.onegravity.rteditor.spans.ParagraphStyleSpan paragraphStyleSpan = (ParagraphStyleSpan) style;
            switch(paragraphStyleSpan.getValue())
            {
                case H1: return ParagraphType.H1;
                case H2: return ParagraphType.H2;
                case H3: return ParagraphType.H3;
                case H4: return ParagraphType.H4;
                case H5: return ParagraphType.H5;
                case H6: return ParagraphType.H6;

                case P:
                default:
                    return ParagraphType.P;
            }
        } else {
            return style instanceof BulletSpan ? ParagraphType.BULLET :
                   style instanceof NumberSpan ? ParagraphType.NUMBERING :
                   style instanceof IndentationSpan ? ParagraphType.INDENTATION_UL : null;
        }
    }

    final private String mStartTag;
    final private String mEndTag;
    final private boolean mIsAlignment;
    final private String mListStartTag;
    final private String mListEndTag;
    final private boolean mEndTagAddsLineBreak;
    final private boolean mIsParagraphStyle;

    private ParagraphType(String startTag, String endTag, String listStartTag,
                          String listEndTag, boolean isAlignment, boolean endTagAddsLineBreak, boolean isParagraphStyle) {
        mStartTag = startTag;
        mEndTag = endTag;
        mListStartTag = listStartTag;
        mListEndTag = listEndTag;
        mIsAlignment = isAlignment;
        mEndTagAddsLineBreak = endTagAddsLineBreak;
        mIsParagraphStyle = isParagraphStyle;
    }

    public boolean isUndefined() {
        return this == ParagraphType.NONE;
    }

    public boolean isAlignment() {
        return mIsAlignment;
    }

    public boolean isParagraphStyle()
    {
        return mIsParagraphStyle;
    }

    public boolean isBullet() {
        return this == ParagraphType.BULLET;
    }

    public boolean isNumbering() {
        return this == ParagraphType.NUMBERING;
    }

    public boolean isIndentation() {
        return this == ParagraphType.INDENTATION_UL || this == ParagraphType.INDENTATION_OL;
    }

    public String getStartTag() {
        return mStartTag;
    }

    public String getEndTag() {
        return mEndTag;
    }

    public String getListStartTag() {
        return mListStartTag;
    }

    public String getListEndTag() {
        return mListEndTag;
    }

    public boolean endTagAddsLineBreak() {
        return mEndTagAddsLineBreak;
    }
}