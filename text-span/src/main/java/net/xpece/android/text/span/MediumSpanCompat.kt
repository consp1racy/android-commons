@file:JvmName("MediumSpanCompat")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan

@Deprecated(
    message = "Every span must be a unique instance.",
    replaceWith = ReplaceWith("MediumSpanCompat()"),
    level = DeprecationLevel.ERROR
)
@get:JvmName("getInstance")
val MediumSpanCompat: Any = MediumSpanCompat()

@JvmName("create")
fun MediumSpanCompat(): MetricAffectingSpan = MediumSpanCompatFactory("sans-serif-medium")

private val MediumSpanCompatFactory = when {
    SDK_INT >= 21 -> ::TypefaceSpan
    else -> ::FakeMediumSpan
}

@Suppress("UNUSED_PARAMETER")
private fun FakeMediumSpan(ignore: String): MetricAffectingSpan {
    return StyleSpan(Typeface.BOLD)
}
