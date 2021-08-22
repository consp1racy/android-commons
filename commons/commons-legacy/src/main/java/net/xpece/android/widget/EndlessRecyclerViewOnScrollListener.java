package net.xpece.android.widget;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerViewOnScrollListener.class.getSimpleName();

    private int mPreviousTotal = 0; // The total number of items in the dataset after the last load
    private boolean mLoading = true; // True if we are still waiting for the last set of data to load.
    private int mVisibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int mFirstVisibleItem, mVisibleItemCount, mTotalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerViewOnScrollListener(LinearLayoutManager linearLayoutManager, int visibleThreshold) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.mVisibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }
        if (!mLoading && (mTotalItemCount - mVisibleItemCount)
            <= (mFirstVisibleItem + mVisibleThreshold)) {
            // End has been reached

            // Do something
            onLoadMore();

            mLoading = true;
        }
    }

    public abstract void onLoadMore();
}
