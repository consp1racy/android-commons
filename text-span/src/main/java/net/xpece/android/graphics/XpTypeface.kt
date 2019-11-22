@file:JvmName("XpTypeface")

package net.xpece.android.graphics

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi

@JvmName("create")
fun Typeface.withExactStyle(
    context: Context,
    weight: Int,
    italic: Boolean = isItalic
): Typeface = createInternal(context, this, weight, italic)

@JvmName("create")
@RequiresApi(21)
fun Typeface.withExactStyle(
    weight: Int,
    italic: Boolean = isItalic
): Typeface = createInternal(null, this, weight, italic)

private fun createInternal(
    context: Context?,
    family: Typeface,
    @IntRange(from = 1, to = 1000) weight: Int,
    italic: Boolean
): Typeface = try {
    when {
        Build.VERSION.SDK_INT >= 28 -> {
            Typeface.create(family, weight, italic)
        }
        Build.VERSION.SDK_INT >= 26 -> {
            WeightTypefaceOreo.create(family, weight, italic)
        }
        Build.VERSION.SDK_INT >= 21 -> {
            WeightTypefaceLollipop.create(family, weight, italic)
        }
        else -> {
            WeightTypefaceLegacy.create(context!!, family, weight, italic)
        }
    }
} catch (ex: Throwable) {
    Log.w("XpTypeface", "Failed to apply font weight.", ex)
    family
}
