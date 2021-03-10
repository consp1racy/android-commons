@file:JvmName("XpTypeface")

package net.xpece.android.graphics

import android.content.Context
import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi

@JvmName("create")
@JvmOverloads
fun Typeface.withExactStyle(
    context: Context,
    weight: Int,
    italic: Boolean = isItalic
): Typeface = Factory(context, this, weight, italic)

@RequiresApi(21)
@JvmName("create")
@JvmOverloads
fun Typeface.withExactStyle(
    weight: Int,
    italic: Boolean = isItalic
): Typeface = Factory(null, this, weight, italic)

private val Factory: TypefaceFactory = createFactory()

private fun createFactory(): TypefaceFactory = when {
    SDK_INT >= 28 -> TypefaceFactory { _, family, weight, italic ->
        Typeface.create(family, weight, italic)
    }
    SDK_INT >= 26 -> TypefaceFactory { _, family, weight, italic ->
        WeightTypefaceOreo.create(family, weight, italic)
    }
    SDK_INT >= 21 -> TypefaceFactory { _, family, weight, italic ->
        WeightTypefaceLollipop.create(family, weight, italic)
    }
    else -> TypefaceFactory { context, family, weight, italic ->
        WeightTypefaceLegacy.create(context!!, family, weight, italic)
    }
}

private fun interface TypefaceFactory {

    operator fun invoke(
        context: Context?,
        family: Typeface,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface
}
