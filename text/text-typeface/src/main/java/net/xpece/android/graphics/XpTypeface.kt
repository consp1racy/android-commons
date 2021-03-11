@file:JvmName("XpTypeface")

package net.xpece.android.graphics

import android.content.Context
import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi

/**
 * Creates a typeface object that best matches the specified existing typeface and the specified
 * weight and italic style
 *
 * Below are numerical values and corresponding common weight names.
 *
 *     | Value | Common weight name |
 *     |:-----:|:------------------:|
 *     | 100   | Thin               |
 *     | 200   | Extra Light        |
 *     | 300   | Light              |
 *     | 400   | Normal             |
 *     | 500   | Medium             |
 *     | 600   | Semi Bold          |
 *     | 700   | Bold               |
 *     | 800   | Extra Bold         |
 *     | 900   | Black              |
 *
 * This method is thread safe.
 *
 * @receiver An existing [Typeface] object.
 * @param weight The desired weight to be drawn.
 * @param italic `true` if italic style is desired to be drawn. Otherwise, `false`.
 * @return A [Typeface] object for drawing specified weight and italic style.
 *
 * @see Typeface.getWeight
 * @see Typeface.isItalic
 */
@JvmName("create")
@JvmOverloads
fun Typeface.withExactStyle(
    context: Context,
    @IntRange(from = 1, to = 1000) weight: Int,
    italic: Boolean = isItalic
): Typeface = Factory(context, this, weight, italic)

/**
 * Creates a typeface object that best matches the specified existing typeface and the specified
 * weight and italic style
 *
 * Below are numerical values and corresponding common weight names.
 *
 *     | Value | Common weight name |
 *     |:-----:|:------------------:|
 *     | 100   | Thin               |
 *     | 200   | Extra Light        |
 *     | 300   | Light              |
 *     | 400   | Normal             |
 *     | 500   | Medium             |
 *     | 600   | Semi Bold          |
 *     | 700   | Bold               |
 *     | 800   | Extra Bold         |
 *     | 900   | Black              |
 *
 * This method is thread safe.
 *
 * @receiver An existing [Typeface] object.
 * @param weight The desired weight to be drawn.
 * @param italic `true` if italic style is desired to be drawn. Otherwise, `false`.
 * @return A [Typeface] object for drawing specified weight and italic style.
 *
 * @see Typeface.getWeight
 * @see Typeface.isItalic
 */
@RequiresApi(21)
@JvmName("create")
@JvmOverloads
fun Typeface.withExactStyle(
    @IntRange(from = 1, to = 1000) weight: Int,
    italic: Boolean = isItalic
): Typeface = Factory(null, this, weight, italic)

private val Factory: (Context?, Typeface, Int, Boolean) -> Typeface = when {
    SDK_INT >= 28 -> { _, family, weight, italic ->
        Typeface.create(family, weight, italic)
    }
    SDK_INT >= 26 -> { _, family, weight, italic ->
        WeightTypefaceOreo.create(family, weight, italic)
    }
    SDK_INT >= 21 -> { _, family, weight, italic ->
        WeightTypefaceLollipop.create(family, weight, italic)
    }
    else -> { context, family, weight, italic ->
        WeightTypefaceLegacy.create(context!!, family, weight, italic)
    }
}
