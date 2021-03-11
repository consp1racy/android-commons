@file:JvmName("TypefaceSpanCompat")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.text.style.TypefaceSpan
import net.xpece.android.text.span.typeface.asSpan

/**
 * Constructs a [TypefaceSpan] from a [Typeface]. The previous style of the
 * [TextPaint][android.text.TextPaint] is overridden and the style of the typeface is used.
 */
@Deprecated(
    message = "Renamed.",
    replaceWith = ReplaceWith("asSpan()", "net.xpece.android.text.span.typeface.asSpan"),
    level = DeprecationLevel.WARNING
)
fun Typeface.span(): TypefaceSpan {
    return asSpan()
}
