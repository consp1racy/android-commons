package net.xpece.android.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pechanecjr on 21. 12. 2014.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final Rect mPadding = new Rect();

    public SpacingItemDecoration(int padding) {
        mPadding.set(padding, padding, padding, padding);
    }

    public SpacingItemDecoration(int horizontal, int vertical) {
        mPadding.set(horizontal, vertical, horizontal, vertical);
    }

    public SpacingItemDecoration(int left, int top, int right, int bottom) {
        mPadding.set(left, top, right, bottom);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mPadding);
    }
}
