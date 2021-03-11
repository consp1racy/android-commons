@file:JvmName("MediumSpanCompat")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan

/**
 * Singleton instance of a `sans-serif-medium` [TypefaceSpan] on API 21,
 * or bold [StyleSpan] on older platforms.
 */
@Deprecated(
    message = "Every span must be a unique instance.",
    replaceWith = ReplaceWith("MediumSpanCompat()"),
    level = DeprecationLevel.ERROR
)
@get:JvmName("getInstance")
val MediumSpanCompat: Any = MediumSpanCompat()

/**
 * Create instance of a `sans-serif-medium` [TypefaceSpan] on API 21,
 * or bold [StyleSpan] on older platforms.
 */
@JvmName("create")
fun MediumSpanCompat(): MetricAffectingSpan = Factory("sans-serif-medium")

private val Factory = when {
    SDK_INT >= 21 -> ::TypefaceSpan
    else -> ::FakeMediumSpan
}

@Suppress("UNUSED_PARAMETER")
private fun FakeMediumSpan(ignore: String): MetricAffectingSpan {
    return StyleSpan(Typeface.BOLD)
}
