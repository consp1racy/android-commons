package net.xpece.android.text.span

import android.text.style.BulletSpan.STANDARD_GAP_WIDTH
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px
import net.xpece.android.text.span.BulletSpanFactory.Companion.BulletSpanCompatFactory

interface BulletSpanCompat : LeadingMarginSpan {

    /**
     * Get the distance, in pixels, between the bullet point and the paragraph.
     *
     * @return the distance, in pixels, between the bullet point and the paragraph.
     */
    val gapWidth: Int

    /**
     * Get the radius, in pixels, of the bullet point.
     *
     * @return the radius, in pixels, of the bullet point.
     */
    val bulletRadius: Int

    /**
     * Get the bullet point color.
     *
     * @return the bullet point color
     */
    val color: Int

    @Suppress("FunctionName")
    companion object {

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
        @JvmStatic
        fun BulletSpanCompat(
            @Px gapWidth: Int,
            @ColorInt color: Int?,
            @Px bulletRadius: Int,
        ): BulletSpanCompat {
            val wantColor = color != null
            val color = color ?: 0
            return BulletSpanCompatFactory(gapWidth, color, wantColor, bulletRadius)
        }

        /**
         * Creates a [BulletSpanCompat] based on a gap width and a color integer.
         *
         * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
         * @param color    the bullet point color, as a color integer
         */
        @JvmName("create")
        @JvmStatic
        fun BulletSpanCompat(
            @Px gapWidth: Int,
            @ColorInt color: Int,
        ): BulletSpanCompat {
            return BulletSpanCompatFactory(gapWidth, color, true, STANDARD_BULLET_RADIUS)
        }

        /**
         * Creates a [BulletSpanCompat] based on a gap width.
         *
         * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
         */
        @JvmName("create")
        @JvmStatic
        fun BulletSpanCompat(
            @Px gapWidth: Int,
        ): BulletSpanCompat {
            return BulletSpanCompatFactory(gapWidth, 0, false, STANDARD_BULLET_RADIUS)
        }

        /**
         * Creates a [BulletSpanCompat] with the default values.
         */
        @JvmName("create")
        @JvmStatic
        fun BulletSpanCompat(): BulletSpanCompat {
            return BulletSpanCompatFactory(STANDARD_GAP_WIDTH, 0, false, STANDARD_BULLET_RADIUS)
        }
    }
}
