package android.support.design.widget;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.graphics.drawable.DrawableWrapper;

/**
 * @author Eugen on 03.04.2017.
 */

@SuppressWarnings("RestrictedApi")
class AltInsetDrawable extends DrawableWrapper {
    public static AltInsetDrawable create(final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new AltInsetDrawableApi21(drawable, insetLeft, insetTop, insetRight, insetBottom);
        } else {
            return new AltInsetDrawable(drawable, insetLeft, insetTop, insetRight, insetBottom);
        }
    }

    public static AltInsetDrawable create(final Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new AltInsetDrawableApi21(drawable);
        } else {
            return new AltInsetDrawable(drawable);
        }
    }

    private final Rect mTmpRect = new Rect();

    private Rect mInset = new Rect();

    protected AltInsetDrawable(final Drawable drawable) {
        super(drawable);
    }

    protected AltInsetDrawable(final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
        super(drawable);
        mInset.set(insetLeft, insetTop, insetRight, insetBottom);
    }

    public void set(int left, int top, int right, int bottom) {
        mInset.left = left;
        mInset.top = top;
        mInset.right = right;
        mInset.bottom = bottom;
    }

    @Override
    protected void onBoundsChange(final Rect bounds) {
        final Rect r = mTmpRect; // DO NOT MODIFY bounds ON LOLLIPOP!
        r.set(bounds);
        r.left += mInset.left;
        r.top += mInset.top;
        r.right -= mInset.right;
        r.bottom -= mInset.bottom;
        super.onBoundsChange(r);
    }

    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth() + mInset.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight() + mInset.height();
    }

    @Override
    public int getMinimumWidth() {
        return super.getMinimumWidth() + mInset.width();
    }

    @Override
    public int getMinimumHeight() {
        return super.getMinimumHeight() + mInset.height();
    }

    @Override
    public boolean getPadding(final Rect padding) {
        final boolean value = super.getPadding(padding);
        padding.left += mInset.left;
        padding.top += mInset.top;
        padding.right += mInset.right;
        padding.bottom += mInset.bottom;
        return value || !mInset.isEmpty();
    }
}
