@file:Suppress("FunctionName")

package net.xpece.android.text.span.bullet

import androidx.annotation.ColorInt
import androidx.annotation.Px
import net.xpece.android.text.span.bullet.BulletSpanCompat.create as Factory

/**
 * Creates a [BulletSpanCompat] based on a gap width and a bullet radius.
 *
 * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
 * @param bulletRadius the radius of the bullet point, in pixels.
 */
@Deprecated(
    message = "Null color is no longer supported.",
    replaceWith = ReplaceWith("BulletSpanCompat(gapWidth, bulletRadius)"),
    level = DeprecationLevel.ERROR
)
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @Px bulletRadius: Int,
    @Suppress("UNUSED_PARAMETER") color: Nothing?,
): BulletSpanCompat {
    return Factory(gapWidth, bulletRadius)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width, a bullet radius and a color integer.
 *
 * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
 * @param bulletRadius the radius of the bullet point, in pixels.
 * @param color        the bullet point color, as a color integer.
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @Px bulletRadius: Int,
    @ColorInt color: Int,
): BulletSpanCompat {
    return Factory(gapWidth, bulletRadius, color)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width and a bullet radius.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 * @param bulletRadius the radius of the bullet point, in pixels.
 */
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @Px bulletRadius: Int,
): BulletSpanCompat {
    return Factory(gapWidth, bulletRadius)
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
