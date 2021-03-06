package android.support.v7.widget;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

/**
 * @author Eugen on 25.04.2017.
 */

public abstract class FullWidthGridItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = FullWidthGridItemDecoration.class.getSimpleName();

    public abstract boolean isItemInset(@NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state);

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
        final boolean itemInset = isItemInset(view, parent, state);
        if (itemInset) {
            final FullWidthGridLayoutManager layout;
            try {
                layout = (FullWidthGridLayoutManager) parent.getLayoutManager();
            } catch (ClassCastException ex) {
                Log.e(TAG, parent.getLayoutManager() + " is not supported by " + this.getClass() + ".");
                super.getItemOffsets(outRect, view, parent, state);
                return;
            }

            final int spanCount = layout.getSpanCount();
            final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            final int spanSize = lp.getSpanSize();
            final int spanIndex = lp.getSpanIndex() % spanCount;

            final boolean firstSpan = spanIndex == 0;
            final boolean lastSpan = spanIndex + spanSize == spanCount;
            if (firstSpan || lastSpan) {
                final int paddingStart;
                final int paddingEnd;
                if (firstSpan) {
                    paddingStart = ViewCompat.getPaddingStart(parent);
                } else {
                    paddingStart = 0;
                }
                if (lastSpan) {
                    paddingEnd = ViewCompat.getPaddingEnd(parent);
                } else {
                    paddingEnd = 0;
                }
                final boolean rtl = ViewCompat.getLayoutDirection(parent) == ViewCompat.LAYOUT_DIRECTION_RTL;
                if (rtl) {
                    outRect.set(paddingEnd, 0, paddingStart, 0);
                } else {
                    outRect.set(paddingStart, 0, paddingEnd, 0);
                }
            } else {
                outRect.setEmpty();
            }
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

}
