@file:JvmName("TypefaceSpanCompat")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.TypefaceSpan

@JvmName("create")
fun Typeface.span(): TypefaceSpan {
    return if (SDK_INT >= 28) {
        TypefaceSpan(this)
    } else {
        TypefaceSpanCompatImpl(this)
    }
}
