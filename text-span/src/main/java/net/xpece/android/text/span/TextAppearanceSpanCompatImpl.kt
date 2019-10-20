/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.xpece.android.text.span

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * Sets the text color, size, style, and typeface to match a TextAppearance
 * resource.
 *
 * Added support for theme attributes in color selectors.
 */
internal class TextAppearanceSpanCompatImpl : MetricAffectingSpan {

    /**
     * Returns the typeface family specified by this span, or `null`
     * if it does not specify one.
     */
    val family: String?
    /**
     * Returns the text style specified by this span, or `0`
     * if it does not specify one.
     */
    val textStyle: Int
    /**
     * Returns the text size specified by this span, or `-1`
     * if it does not specify one.
     */
    val textSize: Int
    /**
     * Returns the text color specified by this span, or `null`
     * if it does not specify one.
     */
    val textColor: ColorStateList?
    /**
     * Returns the link color specified by this span, or `null`
     * if it does not specify one.
     */
    val linkTextColor: ColorStateList?

    /**
     * Uses the specified TextAppearance resource to determine the
     * text appearance.  The `appearance` should be, for example,
     * `android.R.style.TextAppearance_Small`.
     */
    constructor(context: Context, appearance: Int) : this(context, appearance, -1)

    /**
     * Uses the specified TextAppearance resource to determine the
     * text appearance, and the specified text color resource
     * to determine the color.  The `appearance` should be,
     * for example, `android.R.style.TextAppearance_Small`,
     * and the `colorList` should be, for example,
     * `android.R.styleable.Theme_textColorPrimary`.
     */
    private constructor(context: Context, appearance: Int, colorList: Int) {
        if (colorList >= 0) {
            throw UnsupportedOperationException()
        }

        val a = context.obtainStyledAttributes(appearance, R.styleable.TextAppearanceSpan)

        textColor = a.getColorStateList(R.styleable.TextAppearanceSpan_android_textColor)
        linkTextColor = a.getColorStateList(R.styleable.TextAppearanceSpan_android_textColorLink)
        textSize = a.getDimensionPixelSize(R.styleable.TextAppearanceSpan_android_textSize, -1)

        textStyle = a.getInt(R.styleable.TextAppearanceSpan_android_textStyle, 0)
        val family = a.getString(R.styleable.TextAppearanceSpan_android_fontFamily)
        if (family != null) {
            this.family = family
        } else {
            val tf = a.getInt(R.styleable.TextAppearanceSpan_android_typeface, 0)
            when (tf) {
                1 -> this.family = "sans"
                2 -> this.family = "serif"
                3 -> this.family = "monospace"
                else -> this.family = null
            }
        }

        a.recycle()
    }

    override fun updateDrawState(ds: TextPaint) {
        updateMeasureState(ds)

        if (textColor != null) {
            ds.color = textColor.getColorForState(ds.drawableState, 0)
        }

        if (linkTextColor != null) {
            ds.linkColor = linkTextColor.getColorForState(ds.drawableState, 0)
        }
    }

    override fun updateMeasureState(ds: TextPaint) {
        if (family != null || textStyle != 0) {
            var tf: Typeface? = ds.typeface
            var style = 0

            if (tf != null) {
                style = tf.style
            }

            style = style or textStyle

            if (family != null) {
                tf = Typeface.create(family, style)
            } else if (tf == null) {
                tf = Typeface.defaultFromStyle(style)
            } else {
                tf = Typeface.create(tf, style)
            }

            val fake = style and tf!!.style.inv()

            if (fake and Typeface.BOLD != 0) {
                ds.isFakeBoldText = true
            }

            if (fake and Typeface.ITALIC != 0) {
                ds.textSkewX = -0.25f
            }

            ds.typeface = tf
        }

        if (textSize > 0) {
            ds.textSize = textSize.toFloat()
        }
    }
}
