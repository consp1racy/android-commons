package android.support.design.widget;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;

/**
 * @author Eugen on 03.04.2017.
 */

class InsetDrawable2 extends DrawableWrapper {

    private Rect mInset = new Rect();

    public InsetDrawable2(final Drawable drawable) {
        super(drawable);
    }

    public InsetDrawable2(final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
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
        bounds.left += mInset.left;
        bounds.top += mInset.top;
        bounds.right -= mInset.right;
        bounds.bottom -= mInset.bottom;
        super.onBoundsChange(bounds);
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
