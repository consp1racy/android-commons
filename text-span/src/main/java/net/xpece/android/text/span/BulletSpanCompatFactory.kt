@file:JvmName("BulletSpanCompatFactory")
@file:Suppress("FunctionName")
@file:RestrictTo(RestrictTo.Scope.LIBRARY)

package net.xpece.android.text.span

import android.os.Build.VERSION.SDK_INT
import android.os.Parcel
import android.text.style.BulletSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo

import net.xpece.android.text.span.BulletSpanCompatFactory as Factory

// Bullet is slightly bigger to avoid aliasing artifacts on mdpi devices.
private const val STANDARD_BULLET_RADIUS = 4

/**
 * Creates a [BulletSpanCompat] based on a gap width and a color integer.
 *
 * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
 * @param color        the bullet point color, as a color integer.
 * @param bulletRadius the radius of the bullet point, in pixels.
 */
@Suppress("NAME_SHADOWING")
@JvmName("create")
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @ColorInt color: Int?,
    @Px bulletRadius: Int,
): BulletSpanCompat {
    val wantColor = color != null
    val color = color ?: 0
    return Factory(gapWidth, color, wantColor, bulletRadius)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width and a color integer.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 * @param color    the bullet point color, as a color integer
 */
@JvmName("create")
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @ColorInt color: Int,
): BulletSpanCompat {
    return Factory(gapWidth, color, true, STANDARD_BULLET_RADIUS)
}

/**
 * Creates a [BulletSpanCompat] based on a gap width.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 */
@JvmName("create")
fun BulletSpanCompat(
    @Px gapWidth: Int,
): BulletSpanCompat {
    return Factory(gapWidth, 0, false, STANDARD_BULLET_RADIUS)
}

/**
 * Creates a [BulletSpanCompat] with the default values.
 */
@JvmName("create")
fun BulletSpanCompat(): BulletSpanCompat {
    return Factory(BulletSpan.STANDARD_GAP_WIDTH, 0, false, STANDARD_BULLET_RADIUS)
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
@get:JvmName("getInstance")
internal val BulletSpanCompatFactory = when {
    SDK_INT >= 28 -> ::BulletSpanPieCompat
    else -> ::BulletSpanKitkat
}

@RequiresApi(28)
private fun BulletSpanPieCompat(
    gapWidth: Int,
    color: Int,
    wantColor: Boolean,
    bulletRadius: Int
): BulletSpanCompat {
    return try {
        BulletSpanPieParcel(gapWidth, color, wantColor, bulletRadius)
    } catch (_: Throwable) {
        BulletSpanPie(gapWidth, color, wantColor, bulletRadius)
    }
}

@RequiresApi(28)
private fun BulletSpanPieParcel(
    gapWidth: Int,
    color: Int,
    wantColor: Boolean,
    bulletRadius: Int,
): BulletSpanCompat {
    val parcel = Parcel.obtain()
    try {
        parcel.writeInt(gapWidth)
        parcel.writeInt(color)
        parcel.writeInt(if (wantColor) 1 else 0)
        parcel.writeInt(bulletRadius)
        parcel.setDataPosition(0)
        return BulletSpanPie(parcel)
    } finally {
        parcel.recycle();
    }
}
