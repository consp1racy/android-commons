package net.xpece.android.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PrefetchingLinearLayoutManager extends LinearLayoutManager {
    public PrefetchingLinearLayoutManager(Context context) {
        super(context);
    }

    public PrefetchingLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Keep
    public PrefetchingLinearLayoutManager(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        final int value = super.getExtraLayoutSpace(state);
        if (value > 0) return value;
        return 1;
        // Large enough to prefetch one item on each side,
        // small enough not to mess up PagerSnapHelper behavior.
//        return mOrientationHelper.getTotalSpace();
    }
}
