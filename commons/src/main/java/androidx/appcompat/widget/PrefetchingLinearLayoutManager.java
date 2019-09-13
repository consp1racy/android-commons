package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * @deprecated Use {@link androidx.recyclerview.widget.PrefetchingLinearLayoutManager}.
 */
@Deprecated
public class PrefetchingLinearLayoutManager extends androidx.recyclerview.widget.PrefetchingLinearLayoutManager {

    public PrefetchingLinearLayoutManager(Context context) {
        super(context);
    }

    public PrefetchingLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public PrefetchingLinearLayoutManager(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
