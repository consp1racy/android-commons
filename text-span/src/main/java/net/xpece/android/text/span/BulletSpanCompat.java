package net.xpece.android.text.span;

import android.text.style.LeadingMarginSpan;

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
}
