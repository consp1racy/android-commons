package net.xpece.android.text.span;

import androidx.annotation.ColorInt;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;

/**
 * {@inheritDoc}
 *
 * @deprecated Use {@link net.xpece.android.text.span.bullet.BulletSpanCompat} instead.
 */
@SuppressWarnings({"DeprecatedIsStillUsed", "deprecation"})
@Deprecated
public interface BulletSpanCompat extends net.xpece.android.text.span.bullet.BulletSpanCompat {

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width, a bullet radius and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param color        the bullet point color, as a color integer.
     * @param bulletRadius the radius of the bullet point, in pixels.
     * @deprecated Binary compatibility.
     */
    @Deprecated
    static Object create(
            @Px int gapWidth,
            @ColorInt int color,
            @Px int bulletRadius) {
        return BulletSpanCompatFactory.create(gapWidth, color, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a bullet radius.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param bulletRadius the radius of the bullet point, in pixels.
     * @deprecated Binary compatibility.
     */
    @Deprecated
    static Object createWithRadius(
            @Px int gapWidth,
            @Px int bulletRadius) {
        return BulletSpanCompatFactory.create(gapWidth, null, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @param color    the bullet point color, as a color integer
     * @deprecated Binary compatibility.
     */
    @Deprecated
    static Object createWithColor(
            @Px int gapWidth,
            @ColorInt int color) {
        return BulletSpanCompatFactory.create(gapWidth, color);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @deprecated Always specify gapWidth and bulletRadius.
     */
    @Deprecated
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static BulletSpanCompat create(
            @Px int gapWidth) {
        return BulletSpanCompatFactory.create(gapWidth);
    }

    /**
     * Creates a {@link BulletSpanCompat} with the default values.
     *
     * @deprecated Always specify gapWidth and bulletRadius.
     */
    @Deprecated
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static BulletSpanCompat create() {
        return BulletSpanCompatFactory.create();
    }
}
