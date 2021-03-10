package net.xpece.android.text.span;

import androidx.annotation.ColorInt;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;

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
 * <li><b>color</b> - the bullet point color. By default, the bullet point color is 0 - no color,
 * so it uses the TextView's text color.</li>
 * <li><b>bullet radius</b> - the radius, in pixels, of the bullet point. Default value is
 * 4px.</li>
 * </ul>
 * For example, a BulletSpan using the default values can be constructed like this:
 * <pre>{@code
 *  SpannableString string = new SpannableString("Text with\nBullet point");
 * string.setSpan(new BulletSpan(), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);}</pre>
 * <img src="{@docRoot}reference/android/images/text/style/defaultbulletspan.png" />
 * <figcaption>BulletSpan constructed with default values.</figcaption>
 * <p>
 * <p>
 * To construct a BulletSpan with a gap width of 40px, green bullet point and bullet radius of
 * 20px:
 * <pre>{@code
 *  SpannableString string = new SpannableString("Text with\nBullet point");
 * string.setSpan(new BulletSpan(40, color, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);}</pre>
 * <img src="{@docRoot}reference/android/images/text/style/custombulletspan.png" />
 * <figcaption>Customized BulletSpan.</figcaption>
 *
 * @deprecated Use {@link net.xpece.android.text.span.bullet.BulletSpanCompat} instead.
 */
@SuppressWarnings({"DeprecatedIsStillUsed", "deprecation"})
@Deprecated
public interface BulletSpanCompat extends net.xpece.android.text.span.bullet.BulletSpanCompat {

    /**
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
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
     * Creates a {@link BulletSpanCompat} based on a gap width and a color integer.
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
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static BulletSpanCompat create(
            @Px int gapWidth) {
        return BulletSpanCompatFactory.create(gapWidth);
    }

    /**
     * Creates a {@link BulletSpanCompat} with the default values.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static BulletSpanCompat create() {
        return BulletSpanCompatFactory.create();
    }
}
