@file:JvmName("TextAppearanceSpanCompat")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.text.style.TextAppearanceSpan
import androidx.annotation.StyleRes

/**
 * Sets the text appearance using the given `TextAppearanceSpanCompat` attributes.
 * By default `TextAppearanceSpanCompat` only changes the specified attributes in XML.
 * `textColorHighlight`, `textColorHint`, `textAllCaps` and `fallbackLineSpacing`
 * are not supported by `TextAppearanceSpanCompat`.
 *
 * @see android.widget.TextView.setTextAppearance
 * @see android.R.attr.fontFamily
 * @see android.R.attr.textColor
 * @see android.R.attr.textColorLink
 * @see android.R.attr.textFontWeight
 * @see android.R.attr.textSize
 * @see android.R.attr.textStyle
 * @see android.R.attr.typeface
 * @see android.R.attr.shadowColor
 * @see android.R.attr.shadowDx
 * @see android.R.attr.shadowDy
 * @see android.R.attr.shadowRadius
 * @see android.R.attr.elegantTextHeight
 * @see android.R.attr.letterSpacing
 * @see android.R.attr.fontFeatureSettings
 * @see android.R.attr.fontVariationSettings
 */
@JvmName("create")
fun TextAppearanceSpanCompat(context: Context, @StyleRes appearance: Int): Any {
    return Factory(context, appearance)
}

private val Factory = when {
    SDK_INT >= 29 -> ::TextAppearanceSpan
    else -> ::TextAppearanceSpanCompatImpl
}
