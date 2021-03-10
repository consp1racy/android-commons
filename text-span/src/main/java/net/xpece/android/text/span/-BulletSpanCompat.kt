@file:Suppress("FunctionName", "DEPRECATION")

package net.xpece.android.text.span

import androidx.annotation.ColorInt
import androidx.annotation.Px
import net.xpece.android.text.span.BulletSpanCompatFactory.create as Factory

/**
 * Creates a [BulletSpanCompat] based on a gap width, a bullet radius and a color integer.
 *
 * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
 * @param color        the bullet point color, as a color integer.
 * @param bulletRadius the radius of the bullet point, in pixels.
 */
@Deprecated(
    message = "Moved, changed signature.",
    replaceWith = ReplaceWith(
        "BulletSpanCompat(gapWidth, bulletRadius, color)",
        "net.xpece.android.text.span.bullet.BulletSpanCompat",
    ),
    level = DeprecationLevel.WARNING
)
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
@Deprecated(
    message = "Moved, changed signature.",
    replaceWith = ReplaceWith(
        "BulletSpanCompat(gapWidth, 4, color)",
        "net.xpece.android.text.span.bullet.BulletSpanCompat",
    ),
    level = DeprecationLevel.WARNING
)
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
@Deprecated(
    message = "Moved.",
    replaceWith = ReplaceWith(
        "BulletSpanCompat(gapWidth)",
        "net.xpece.android.text.span.bullet.BulletSpanCompat",
    ),
    level = DeprecationLevel.WARNING
)
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
@Deprecated(
    message = "Moved.",
    replaceWith = ReplaceWith(
        "BulletSpanCompat()",
        "net.xpece.android.text.span.bullet.BulletSpanCompat",
    ),
    level = DeprecationLevel.WARNING
)
@JvmName("create")
@JvmSynthetic
fun BulletSpanCompat(): BulletSpanCompat {
    return Factory()
}
