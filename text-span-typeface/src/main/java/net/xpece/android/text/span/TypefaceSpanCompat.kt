@file:JvmName("TypefaceSpanCompat")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.TypefaceSpan

@Deprecated(
    message = "Renamed.",
    replaceWith = ReplaceWith("asSpan()", "net.xpece.android.text.span.asSpan"),
    level = DeprecationLevel.ERROR
)
fun Typeface.span(): TypefaceSpan {
    return asSpan()
}

@JvmName("create")
fun Typeface.asSpan(): TypefaceSpan {
    return Factory(this)
}

private val Factory: (Typeface) -> TypefaceSpan = when {
    SDK_INT >= 28 -> ::TypefaceSpan
    else -> ::TypefaceSpanCompatImpl
}
