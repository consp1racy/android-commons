@file:JvmName("LayoutCompat")

package net.xpece.android.text

import android.text.Layout
import android.text.StaticLayout

private const val EXTRA_ROUNDING = 0.5

/**
 * Get the extra spacing below this line.
 *
 * The implementation is based on [StaticLayout] and some assumptions.
 */
@JvmName("getLineExtra")
fun Layout.getLineExtraCompat(line: Int) : Int {
    // This is changed nowhere in AOSP to true, at first glance.
    val addLastLineLineSpacing = false
    val lastLine = line == lineCount - 1
    // We're assuming included bottom padding.
    // Otherwise we couldn't compute this easily.
    val below = getLineBottom(line)
    val above = getLineAscent(line)
    val spacingmult = spacingMultiplier
    val spacingadd = spacingAdd
    val needMultiply = spacingmult != 1f || spacingadd != 0f

    val extra: Int
    if (needMultiply && (addLastLineLineSpacing || !lastLine)) {
        val ex = (below - above) * (spacingmult - 1) + spacingadd
        if (ex >= 0) {
            extra = (ex + EXTRA_ROUNDING).toInt()
        } else {
            extra = -((-ex + EXTRA_ROUNDING).toInt())
        }
    } else {
        extra = 0
    }
    return extra
}
