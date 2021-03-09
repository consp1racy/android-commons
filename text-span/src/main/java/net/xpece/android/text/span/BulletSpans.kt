@file:JvmName("BulletSpans")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.os.Build.VERSION.SDK_INT
import android.os.Parcel
import android.text.style.BulletSpan
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi

// Bullet is slightly bigger to avoid aliasing artifacts on mdpi devices.
private const val STANDARD_BULLET_RADIUS = 4

private val FACTORY: BulletSpanFactory = when {
    SDK_INT >= 28 -> BulletSpanPieFactory
    else -> BulletSpanKitkatFactory
}

/**
 * Creates a [BulletSpan] based on a gap width and a color integer.
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
): LeadingMarginSpan {
    val wantColor = color != null
    val color = color ?: 0
    return FACTORY(gapWidth, color, wantColor, bulletRadius)
}

/**
 * Creates a [BulletSpan] based on a gap width and a color integer.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 * @param color    the bullet point color, as a color integer
 */
@JvmName("create")
fun BulletSpanCompat(
    @Px gapWidth: Int,
    @ColorInt color: Int,
): LeadingMarginSpan {
    return FACTORY(gapWidth, color, true, STANDARD_BULLET_RADIUS)
}

/**
 * Creates a [BulletSpan] based on a gap width.
 *
 * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
 */
@JvmName("create")
fun BulletSpanCompat(
    @Px gapWidth: Int,
): LeadingMarginSpan {
    return FACTORY(gapWidth, 0, false, STANDARD_BULLET_RADIUS)
}

private fun interface BulletSpanFactory {

    operator fun invoke(
        @Px gapWidth: Int,
        @ColorInt color: Int,
        wantColor: Boolean,
        @Px bulletRadius: Int,
    ): BulletSpanCompat
}

private object BulletSpanKitkatFactory : BulletSpanFactory {

    override fun invoke(
        gapWidth: Int,
        color: Int,
        wantColor: Boolean,
        bulletRadius: Int
    ): BulletSpanCompat {
        return BulletSpanKitkat(gapWidth, color, wantColor, bulletRadius)
    }
}

@RequiresApi(28)
private object BulletSpanPieFactory : BulletSpanFactory {

    override fun invoke(
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
}

@RequiresApi(28)
private object BulletSpanPieParcel : BulletSpanFactory {

    override fun invoke(
        gapWidth: Int,
        color: Int,
        wantColor: Boolean,
        bulletRadius: Int
    ): BulletSpanCompat {
        val parcel = Parcel.obtain()
        try {
            parcel.writeInt(gapWidth)
            parcel.writeInt(0)
            parcel.writeInt(0)
            parcel.writeInt(bulletRadius)
            parcel.setDataPosition(0)
            return BulletSpanPie(parcel)
        } finally {
            parcel.recycle();
        }
    }
}
