package net.xpece.android.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.FullWidthGridLayoutManagerImpl;

public class FullWidthGridLayoutManager extends FullWidthGridLayoutManagerImpl {

    public FullWidthGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FullWidthGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FullWidthGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
