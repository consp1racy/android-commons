@file:JvmName("TextAppearanceSpanCompat")

package net.xpece.android.text.span

import android.content.Context
import android.os.Build
import android.text.style.TextAppearanceSpan
import androidx.annotation.StyleRes

@JvmName("create")
fun TextAppearanceSpanCompat(context: Context, @StyleRes appearance: Int): Any {
    return if (Build.VERSION.SDK_INT >= 29) {
        TextAppearanceSpan(context, appearance)
    } else {
        TextAppearanceSpanCompatImpl(context, appearance)
    }
}
