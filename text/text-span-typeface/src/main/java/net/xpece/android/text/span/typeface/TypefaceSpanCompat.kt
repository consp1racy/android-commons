@file:JvmName("TypefaceSpanCompat")

package net.xpece.android.text.span.typeface

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.TypefaceSpan

/**
 * Constructs a [TypefaceSpan] from a [Typeface].
 * The previous style of the [TextPaint][android.text.TextPaint] is overridden
 * and the style of the typeface is used.
 */
@JvmName("create")
fun Typeface.asSpan(): TypefaceSpan {
    return Factory(this)
}

private val Factory: (Typeface) -> TypefaceSpan = when {
    SDK_INT >= 28 -> ::TypefaceSpan
    else -> ::TypefaceSpanCompatImpl
}

/**
 * Returns the typeface set in the span.
 */
val TypefaceSpan.typefaceCompat: Typeface?
    @SuppressLint("NewApi")
    @JvmName("getTypeface")
    get() = try {
        typeface
    } catch (_: Throwable) {
        null
    }
