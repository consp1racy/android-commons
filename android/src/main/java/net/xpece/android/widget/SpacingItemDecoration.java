package net.xpece.android.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pechanecjr on 21. 12. 2014.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

  private float mPadding;

  public SpacingItemDecoration(float padding) {
    mPadding = padding;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int padding = (int) mPadding;
    outRect.set(padding, padding, padding, padding);
  }
}
