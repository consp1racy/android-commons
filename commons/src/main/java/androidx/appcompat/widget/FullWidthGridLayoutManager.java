package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @deprecated Use {@link androidx.recyclerview.widget.FullWidthGridLayoutManager}.
 */
@Deprecated
public class FullWidthGridLayoutManager extends androidx.recyclerview.widget.FullWidthGridLayoutManager {

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
