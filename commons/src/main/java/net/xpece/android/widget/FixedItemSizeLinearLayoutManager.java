package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author Eugen on 15.03.2017.
 */

public class FixedItemSizeLinearLayoutManager extends LinearLayoutManager {

    private int mItemWidth = 0;
    private int mItemHeight = 0;

    public FixedItemSizeLinearLayoutManager(final Context context) {
        super(context);
    }

    public FixedItemSizeLinearLayoutManager(final Context context, final int orientation, final boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Keep
    public FixedItemSizeLinearLayoutManager(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixedItemSizeLinearLayoutManager setItemHeight(final int itemHeight) {
        if (mItemHeight != itemHeight) {
            mItemHeight = itemHeight;
            if (isAttachedToWindow()) {
                requestLayout();
            }
        }
        return this;
    }

    public FixedItemSizeLinearLayoutManager setItemWidth(final int itemWidth) {
        if (mItemWidth != itemWidth) {
            mItemWidth = itemWidth;
            if (isAttachedToWindow()) {
                requestLayout();
            }
        }
        return this;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        final RecyclerView.LayoutParams out = super.generateDefaultLayoutParams();
        if (mItemWidth > 0) out.width = mItemWidth;
        if (mItemHeight > 0) out.height = mItemHeight;
        return out;
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(final Context c, final AttributeSet attrs) {
        final RecyclerView.LayoutParams out = super.generateLayoutParams(c, attrs);
        if (mItemWidth > 0) out.width = mItemWidth;
        if (mItemHeight > 0) out.height = mItemHeight;
        return out;
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(final ViewGroup.LayoutParams lp) {
        final RecyclerView.LayoutParams out = super.generateLayoutParams(lp);
        if (mItemWidth > 0) out.width = mItemWidth;
        if (mItemHeight > 0) out.height = mItemHeight;
        return out;
    }
}
