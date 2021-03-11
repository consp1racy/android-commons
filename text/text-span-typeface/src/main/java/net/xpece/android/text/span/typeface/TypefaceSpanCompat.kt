@file:JvmName("TypefaceSpanCompat")

package net.xpece.android.text.span.typeface

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.TypefaceSpan

/**
 * Constructs a [TypefaceSpan] from a [Typeface]. The previous style of the
 * [TextPaint][android.text.TextPaint] is overridden and the style of the typeface is used.
 */
@JvmName("create")
fun Typeface.asSpan(): TypefaceSpan {
    return Factory(this)
}

private val Factory: (Typeface) -> TypefaceSpan = when {
    SDK_INT >= 28 -> ::TypefaceSpan
    else -> ::TypefaceSpanCompatImpl
}
