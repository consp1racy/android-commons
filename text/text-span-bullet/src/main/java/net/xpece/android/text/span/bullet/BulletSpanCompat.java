package net.xpece.android.text.span.bullet;

import android.text.style.LeadingMarginSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.Px;

/**
 * A span which styles paragraphs as bullet points (respecting layout direction).
 * <p>
 * BulletSpans must be attached from the first character to the last character of a single
 * paragraph, otherwise the bullet point will not be displayed but the first paragraph encountered
 * will have a leading margin.
 * <p>
 * BulletSpans allow configuring the following elements:
 * <ul>
 * <li><b>gap width</b> - the distance, in pixels, between the bullet point and the paragraph.
 * Default value is 2px.</li>
 * <li><b>bullet radius</b> - the radius, in pixels, of the bullet point. Default value is
 * 4px.</li>
 * <li><b>color</b> - the bullet point color. By default, it uses the TextView's text color.</li>
 * </ul>
 */
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
     * Creates a {@link BulletSpanCompat} based on a gap width, a bullet radius and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param bulletRadius the radius of the bullet point, in pixels.
     * @param color        the bullet point color, as a color integer.
     */
    @SuppressWarnings("deprecation")
    static BulletSpanCompat create(
            @Px int gapWidth,
            @Px int bulletRadius,
            @ColorInt int color) {
        return (BulletSpanCompat) net.xpece.android.text.span.BulletSpanCompat
                .create(gapWidth, color, bulletRadius);
    }

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a bullet radius.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param bulletRadius the radius of the bullet point, in pixels.
     */
    @SuppressWarnings("deprecation")
    static BulletSpanCompat create(
            @Px int gapWidth,
            @Px int bulletRadius) {
        return (BulletSpanCompat) net.xpece.android.text.span.BulletSpanCompat
                .createWithRadius(gapWidth, bulletRadius);
    }

    /**
     * Creates a{@link BulletSpanCompat} based on a gap width.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @deprecated Always specify {@code gapWidth} and {@code bulletRadius}.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    static BulletSpanCompat create(@Px int gapWidth) {
        return net.xpece.android.text.span.BulletSpanCompat.create(gapWidth);
    }

    /**
     * Creates a {@link BulletSpanCompat} with the default values.
     *
     * @deprecated Always specify {@code gapWidth} and {@code bulletRadius}.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    static BulletSpanCompat create() {
        return net.xpece.android.text.span.BulletSpanCompat.create();
    }
}
