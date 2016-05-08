package net.xpece.android.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pechanecjr on 21. 12. 2014.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final Rect mPadding = new Rect();

    private final Rect mTempRect = new Rect();
    private final Paint mPaint = new Paint();
    private boolean mPaintPadding;

    public SpacingItemDecoration(int padding) {
        mPadding.set(padding, padding, padding, padding);
        init();
    }

    public SpacingItemDecoration(int horizontal, int vertical) {
        mPadding.set(horizontal, vertical, horizontal, vertical);
        init();
    }

    public SpacingItemDecoration(int left, int top, int right, int bottom) {
        mPadding.set(left, top, right, bottom);
        init();
    }

    private void init() {
        mPaint.setColor(0);
    }

    public boolean getPaintPadding() {
        return mPaintPadding;
    }

    public SpacingItemDecoration setPaintPadding(boolean paintPadding) {
        mPaintPadding = paintPadding;
        return this;
    }

    public SpacingItemDecoration setColor(@ColorInt int color) {
        mPaint.setColor(color);
        return this;
    }

    @ColorInt
    public int getColor() {
        return mPaint.getColor();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mPadding);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.LayoutManager lm = parent.getLayoutManager();
        final Rect rect = mTempRect;
        final Rect padding = mPadding;

        int left;
        int top;
        int right;
        int bottom;

        if (mPaintPadding) {
            left = parent.getPaddingLeft();
            top = parent.getPaddingTop();
            right = parent.getPaddingRight();
            bottom = parent.getPaddingBottom();

            // Left padding.
            rect.set(0, 0, left, c.getHeight());
            c.drawRect(rect, mPaint);

            // Right padding.
            rect.set(c.getWidth() - right, 0, c.getWidth(), c.getHeight());
            c.drawRect(rect, mPaint);

            // Top padding.
            rect.set(left, 0, c.getWidth() - right, top);
            c.drawRect(rect, mPaint);

            // Bottom padding.
            rect.set(left, c.getHeight() - bottom, c.getWidth() - right, c.getHeight());
            c.drawRect(rect, mPaint);
        }

        for (int i = 0, size = parent.getChildCount(); i < size; i++) {
            final View child = parent.getChildAt(i);

            left = lm.getDecoratedLeft(child);
            top = lm.getDecoratedTop(child);
            right = lm.getDecoratedRight(child);
            bottom = lm.getDecoratedBottom(child);

            // Paint top
            if (padding.top > 0) {
                rect.set(left, top, right, top + padding.top);
                c.drawRect(rect, mPaint);
            }

            // Paint bottom
            if (padding.bottom > 0) {
                rect.set(left, bottom - padding.bottom, right, bottom);
                c.drawRect(rect, mPaint);
            }

            // Paint left
            if (padding.left > 0) {
                rect.set(left, top + padding.top, left + padding.left, bottom - padding.bottom);
                c.drawRect(rect, mPaint);
            }

            // Paint right
            if (padding.right > 0) {
                rect.set(right - padding.right, top + padding.top, right, bottom - padding.bottom);
                c.drawRect(rect, mPaint);
            }
        }
    }
}
