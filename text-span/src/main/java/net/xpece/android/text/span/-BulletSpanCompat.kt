@file:Suppress("FunctionName")

package net.xpece.android.text.span

import androidx.annotation.ColorInt
import androidx.annotation.Px
import net.xpece.android.text.span.BulletSpanCompatFactory.create as Factory

/**
 * Creates a [BulletSpanCompat] based on a gap width and a color integer.
 *
 * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
 * @param color        the bullet point color, as a color integer.
 * @param bulletRadius the radius of the bullet point, in pixels.
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @ColorInt color: Int?,
    @Px bulletRadius: Int,
): BulletSpanCompat {
    return Factory(gapWidth, color, bulletRadius)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width and a color integer.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 * @param color    the bullet point color, as a color integer
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @ColorInt color: Int,
): BulletSpanCompat {
    return Factory(gapWidth, color)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
): BulletSpanCompat {
    return Factory(gapWidth)
}

/**
 * Creates a [BulletSpanCompat] with the default values.
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(): BulletSpanCompat {
    return Factory()
}
