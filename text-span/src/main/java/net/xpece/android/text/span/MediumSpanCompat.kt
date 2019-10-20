@file:JvmName("MediumSpanCompat")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan

private val mediumSpanCompatImpl: Any = run {
    if (Build.VERSION.SDK_INT >= 21) {
        TypefaceSpan("sans-serif-medium")
    } else {
        StyleSpan(Typeface.BOLD)
    }
}

@get:JvmName("getInstance")
val MediumSpanCompat: Any
    get() = mediumSpanCompatImpl
