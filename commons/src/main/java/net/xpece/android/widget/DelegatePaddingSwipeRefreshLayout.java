package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;


public class DelegatePaddingSwipeRefreshLayout extends SwipeRefreshLayout {

    private boolean hasPaddingRelative = false;
    private Rect insets;

    public DelegatePaddingSwipeRefreshLayout(final Context context) {
        this(context, null);
    }

    public DelegatePaddingSwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        // TODO Offset progress view.
    }

    @Override
    public void setPadding(final int left, final int top, final int right, final int bottom) {
        hasPaddingRelative = false;
        getInsets().set(left, top, right, bottom);

        final View child = getChildAt(1);
        if (child != null) {
            super.setPadding(0, 0, 0, 0);
            child.setPadding(left, top, right, bottom);

        } else {
            super.setPadding(left, top, right, bottom);
        }
    }

    private Rect getInsets() {
        if (insets == null) {
            insets = new Rect();
        }
        return insets;
    }

    @Override
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setPaddingRelative(final int start, final int top, final int end, final int bottom) {
        hasPaddingRelative = true;
        getInsets().set(start, top, end, bottom);

        final View child = getChildAt(1);
        if (child != null) {
            super.setPaddingRelative(0, 0, 0, 0);
            child.setPaddingRelative(start, top, end, bottom);

            final View progress = getChildAt(0);
            final MarginLayoutParams progressLp = (MarginLayoutParams) progress.getLayoutParams();
            progressLp.topMargin = top;
        } else {
            super.setPaddingRelative(start, top, end, bottom);
        }
    }

    @Override
    @TargetApi(17)
    public void addView(final View child, final int index, final LayoutParams params) {
        super.addView(child, index, params);
        if (hasPaddingRelative) {
            setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    @SuppressWarnings("ResourceType")
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
