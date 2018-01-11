package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * Only used on Lollipop. Ripple over round rect mask has weird outline otherwise.
 *
 * @author Eugen on 06.10.2017.
 */
@RequiresApi(21)
class XpRoundRectRippleDrawable extends RippleDrawable {
    private final float mCornerRadius;

    private final Path mForegroundClippingPath = new Path();

    /**
     * Creates a new ripple drawable with the specified ripple color and
     * optional content and mask drawables.
     *
     * @param color The ripple color
     * @param content The content drawable, may be {@code null}
     * @param mask The mask drawable, may be {@code null}
     */
    public XpRoundRectRippleDrawable(
        @NonNull final ColorStateList color, @Nullable final Drawable content,
        @Nullable final Drawable mask, final float cornerRadius) {
        super(color, content, mask);
        mCornerRadius = cornerRadius;
    }

    @Override
    protected void onBoundsChange(@NonNull final Rect bounds) {
        super.onBoundsChange(bounds);

        final Path path = mForegroundClippingPath;
        path.reset();
        path.addRoundRect(bounds.left, bounds.top, bounds.right, bounds.bottom, mCornerRadius, mCornerRadius, Path.Direction.CW);
    }

    @Override
    public void draw(@NonNull final Canvas canvas) {
        final int save = canvas.save();
        canvas.clipPath(mForegroundClippingPath);

        super.draw(canvas);

        canvas.restoreToCount(save);
    }
}
