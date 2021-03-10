package net.xpece.android.text.span;

import android.text.style.LeadingMarginSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.Px;

public interface BulletSpanCompat extends LeadingMarginSpan {

    /**
     * Get the distance, in pixels, between the bullet point and the paragraph.
     *
     * @return the distance, in pixels, between the bullet point and the paragraph.
     */
    int getGapWidth();

    /**
     * Get the radius, in pixels, of the bullet point.
     *
     * @return the radius, in pixels, of the bullet point.
     */
    int getBulletRadius();

    /**
     * Get the bullet point color.
     *
     * @return the bullet point color
     */
    int getColor();

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param color        the bullet point color, as a color integer.
     * @param bulletRadius the radius of the bullet point, in pixels.
     */
    static BulletSpanCompat create(
            @Px int gapWidth,
            @Nullable @ColorInt Integer color,
            @Px int bulletRadius) {
        return BulletSpanCompatFactory.create(gapWidth, color, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param color        the bullet point color, as a color integer.
     * @param bulletRadius the radius of the bullet point, in pixels.
     */
    static BulletSpanCompat create(
            @Px int gapWidth,
            @ColorInt int color,
            @Px int bulletRadius) {
        return BulletSpanCompatFactory.create(gapWidth, color, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param bulletRadius the radius of the bullet point, in pixels.
     *
     * @deprecated Binary compatibility. Use {@link #create(int, Integer, int)}.
     */
    @Deprecated
    static BulletSpanCompat createWithRadius(
            @Px int gapWidth,
            @Px int bulletRadius) {
        return BulletSpanCompatFactory.create(gapWidth, null, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @param color    the bullet point color, as a color integer
     *
     * @deprecated Binary compatibility. Use {@link #create(int, int)}.
     */
    @Deprecated
    static BulletSpanCompat createWithColor(
            @Px int gapWidth,
            @ColorInt int color) {
        return BulletSpanCompatFactory.create(gapWidth, color);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @param color    the bullet point color, as a color integer
     */
    static BulletSpanCompat create(
            @Px int gapWidth,
            @ColorInt int color) {
        return BulletSpanCompatFactory.create(gapWidth, color);
    }

    /**
     * Creates a{@link BulletSpanCompat} based on a gap width.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     */
    static BulletSpanCompat create(@Px int gapWidth) {
        return BulletSpanCompatFactory.create(gapWidth);
    }

    /**
     * Creates a {@link BulletSpanCompat} with the default values.
     */
    static BulletSpanCompat create() {
        return BulletSpanCompatFactory.create();
    }
}
