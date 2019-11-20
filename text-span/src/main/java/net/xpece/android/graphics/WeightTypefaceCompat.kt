package net.xpece.android.graphics

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.annotation.IntRange

object WeightTypefaceCompat {

    @JvmStatic
    fun create(context: Context, family: Typeface, weight: Int, italic: Boolean): Typeface {
        return createInternal(context, family, weight, italic)
    }

    @PublishedApi
    internal fun createInternal(
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
                WeightTypefaceOreo.createWeightStyle(family, weight, italic)
            }
            else -> {
                @Suppress("NAME_SHADOWING")
                val context = requireNotNull(context)
                WeightTypefaceLegacy.getBestFontFromFamily(context, family, weight, italic)
                    ?: family
            }
        }
    } catch (ex: Throwable) {
        Log.w("WeightTypefaceCompat", "Failed to apply font weight.", ex)
        family
    }
}
