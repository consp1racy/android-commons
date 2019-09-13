package net.xpece.android.widget;

import android.graphics.Rect;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Eugen on 27.04.2017.
 */

public class EndInsetItemDecoration extends RecyclerView.ItemDecoration {
    private final int mInset;

    public EndInsetItemDecoration(int inset) {
        mInset = inset;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        final int count = parent.getAdapter().getItemCount();
        final boolean last = position == count - 1;
        final boolean rtl = ViewCompat.getLayoutDirection(parent) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (!last) {
            // This assumes the items are laid out from start.
            // To avoid screwing up SnapHelpers the offset is added to item's end.
            if (rtl) {
                outRect.set(mInset, 0, 0, 0);
            } else {
                outRect.set(0, 0, mInset, 0);
            }
        } else {
            outRect.setEmpty();
        }
    }
}
