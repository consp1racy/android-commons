/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

/**
 * A {@link RecyclerView.LayoutManager} implementations that lays out items in a grid.
 * <p>
 * By default, each item occupies 1 span. You can change it by providing a custom
 * {@link GridLayoutManager.SpanSizeLookup} instance via {@link #setSpanSizeLookup(GridLayoutManager.SpanSizeLookup)}.
 */
public class FullWidthGridLayoutManager extends LinearLayoutManager {

    private static final boolean DEBUG = false;
    private static final String TAG = "GridLayoutManager";
    public static final int DEFAULT_SPAN_COUNT = -1;
    /**
     * Span size have been changed but we've not done a new layout calculation.
     */
    boolean mPendingSpanCountChange = false;
    int mSpanCount = DEFAULT_SPAN_COUNT;
    /**
     * Right borders for each span.
     * <p>For <b>i-th</b> item start is {@link #mCachedBorders}[i-1] + 1
     * and end is {@link #mCachedBorders}[i].
     */
    int[] mCachedBorders;
    /**
     * Temporary array to keep views in layoutChunk method
     */
    View[] mSet;
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.DefaultSpanSizeLookup();
    // re-used variable to acquire decor insets from RecyclerView
    final Rect mDecorInsets = new Rect();

    /**
     * Constructor used when layout manager is set in XML by RecyclerView attribute
     * "layoutManager". If spanCount is not specified in the XML, it defaults to a
     * single column.
     *
     * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_spanCount
     */
    public FullWidthGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                      int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Properties properties = getProperties(context, attrs, defStyleAttr, defStyleRes);
        setSpanCount(properties.spanCount);
    }

    /**
     * Creates a vertical GridLayoutManager
     *
     * @param context Current context, will be used to access resources.
     * @param spanCount The number of columns in the grid
     */
    public FullWidthGridLayoutManager(Context context, int spanCount) {
        super(context);
        setSpanCount(spanCount);
    }

    /**
     * @param context Current context, will be used to access resources.
     * @param spanCount The number of columns or rows in the grid
     * @param orientation Layout orientation. Should be {@link #HORIZONTAL} or {@link
     * #VERTICAL}.
     * @param reverseLayout When set to true, layouts from end to start.
     */
    public FullWidthGridLayoutManager(Context context, int spanCount, int orientation,
                                      boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        setSpanCount(spanCount);
    }

    /**
     * stackFromEnd is not supported by GridLayoutManager. Consider using
     * {@link #setReverseLayout(boolean)}.
     */
    @Override
    public void setStackFromEnd(boolean stackFromEnd) {
        if (stackFromEnd) {
            throw new UnsupportedOperationException(
                "GridLayoutManager does not support stack from end."
                    + " Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler,
                                           RecyclerView.State state) {
        if (mOrientation == HORIZONTAL) {
            return mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }

        // Row count is one more than the last item's row index.
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler,
                                              RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            return mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }

        // Column count is one more than the last item's column index.
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler,
                                                         RecyclerView.State state, View host, AccessibilityNodeInfoCompat info) {
        ViewGroup.LayoutParams lp = host.getLayoutParams();
        if (!(lp instanceof GridLayoutManager.LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(host, info);
            return;
        }
        GridLayoutManager.LayoutParams glp = (GridLayoutManager.LayoutParams) lp;
        int spanGroupIndex = getSpanGroupIndex(recycler, state, glp.getViewLayoutPosition());
        if (mOrientation == HORIZONTAL) {
            info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                glp.getSpanIndex(), glp.getSpanSize(),
                spanGroupIndex, 1,
                mSpanCount > 1 && glp.getSpanSize() == mSpanCount, false));
        } else { // VERTICAL
            info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                spanGroupIndex, 1,
                glp.getSpanIndex(), glp.getSpanSize(),
                mSpanCount > 1 && glp.getSpanSize() == mSpanCount, false));
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        if (DEBUG) {
            validateChildOrder();
        }
        clearPreLayoutSpanMappingCache();
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        mPendingSpanCountChange = false;
    }

    private void clearPreLayoutSpanMappingCache() {
        mPreLayoutSpanSizeCache.clear();
        mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) getChildAt(i).getLayoutParams();
            final int viewPosition = lp.getViewLayoutPosition();
            mPreLayoutSpanSizeCache.put(viewPosition, lp.getSpanSize());
            mPreLayoutSpanIndexCache.put(viewPosition, lp.getSpanIndex());
        }
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount,
                               Object payload) {
        mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (mOrientation == HORIZONTAL) {
            return new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            return new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new GridLayoutManager.LayoutParams(c, attrs);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new GridLayoutManager.LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new GridLayoutManager.LayoutParams(lp);
        }
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return lp instanceof GridLayoutManager.LayoutParams;
    }

    /**
     * Sets the source to get the number of spans occupied by each item in the adapter.
     *
     * @param spanSizeLookup {@link GridLayoutManager.SpanSizeLookup} instance to be used to query number of spans
     * occupied by each item
     */
    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    /**
     * Returns the current {@link GridLayoutManager.SpanSizeLookup} used by the GridLayoutManager.
     *
     * @return The current {@link GridLayoutManager.SpanSizeLookup} used by the GridLayoutManager.
     */
    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    private void updateMeasurements() {
        int totalSpace;
        if (getOrientation() == VERTICAL) {
            totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
        } else {
            totalSpace = getHeight() - getPaddingBottom() - getPaddingTop();
        }
        calculateItemBorders(totalSpace);
    }

    @Override
    public void setMeasuredDimension(Rect childrenBounds, int wSpec, int hSpec) {
        if (mCachedBorders == null) {
            Log.d(TAG, "mCachedBorders is null.");
            super.setMeasuredDimension(childrenBounds, wSpec, hSpec);
        }
        final int width, height;
        final int horizontalPadding = getPaddingLeft() + getPaddingRight();
        final int verticalPadding = getPaddingTop() + getPaddingBottom();
        if (mOrientation == VERTICAL) {
            final int usedHeight = childrenBounds.height() + verticalPadding;
            height = chooseSize(hSpec, usedHeight, getMinimumHeight());
            width = chooseSize(wSpec, mCachedBorders[mCachedBorders.length - 1] + horizontalPadding,
                getMinimumWidth());
        } else {
            final int usedWidth = childrenBounds.width() + horizontalPadding;
            width = chooseSize(wSpec, usedWidth, getMinimumWidth());
            height = chooseSize(hSpec, mCachedBorders[mCachedBorders.length - 1] + verticalPadding,
                getMinimumHeight());
        }
        setMeasuredDimension(width, height);
    }

    /**
     * @param totalSpace Total available space after padding is removed
     */
    private void calculateItemBorders(int totalSpace) {
        final int paddingStart, paddingEnd;
        if (mOrientation == VERTICAL) {
            paddingStart = getPaddingStart();
            paddingEnd = getPaddingEnd();
        } else {
            paddingStart = getPaddingTop();
            paddingEnd = getPaddingBottom();
        }
        mCachedBorders = calculateItemBorders(mCachedBorders, mSpanCount, totalSpace, paddingStart, paddingEnd);
    }

    /**
     * @param cachedBorders The out array
     * @param spanCount number of spans
     * @param totalSpace total available space after padding is removed
     * @return The updated array. Might be the same instance as the provided array if its size
     * has not changed.
     */
    static int[] calculateItemBorders(int[] cachedBorders, final int spanCount, final int totalSpace, final int paddingStart, final int paddingEnd) {
        if (cachedBorders == null || cachedBorders.length != spanCount + 1
            || cachedBorders[cachedBorders.length - 1] != totalSpace) {
            cachedBorders = new int[spanCount + 1];
        }
        cachedBorders[0] = -paddingStart; // 0;
        int sizePerSpan = totalSpace / spanCount;
        int sizePerSpanRemainder = totalSpace % spanCount;
        int consumedPixels = 0;
        int additionalSize = 0;
        for (int i = 1; i <= spanCount; i++) {
            int itemSize = sizePerSpan;
            additionalSize += sizePerSpanRemainder;
            if (additionalSize > 0 && (spanCount - additionalSize) < sizePerSpanRemainder) {
                itemSize += 1;
                additionalSize -= spanCount;
            }
            consumedPixels += itemSize;
            cachedBorders[i] = consumedPixels;
        }
        cachedBorders[spanCount] += paddingEnd;
        return cachedBorders;
    }

    int getSpaceForSpanRange(int startSpan, int spanSize) {
        if (mOrientation == VERTICAL && isLayoutRTL()) {
            return mCachedBorders[mSpanCount - startSpan]
                - mCachedBorders[mSpanCount - startSpan - spanSize];
        } else {
            return mCachedBorders[startSpan + spanSize] - mCachedBorders[startSpan];
        }
    }

    @Override
    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state,
                       AnchorInfo anchorInfo, int itemDirection) {
        super.onAnchorReady(recycler, state, anchorInfo, itemDirection);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, itemDirection);
        }
        ensureViewSet();
    }

    private void ensureViewSet() {
        if (mSet == null || mSet.length != mSpanCount) {
            mSet = new View[mSpanCount];
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler,
                                             RecyclerView.State state, AnchorInfo anchorInfo, int itemDirection) {
        final boolean layingOutInPrimaryDirection =
            itemDirection == LayoutState.ITEM_DIRECTION_TAIL;
        int span = getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (layingOutInPrimaryDirection) {
            // choose span 0
            while (span > 0 && anchorInfo.mPosition > 0) {
                anchorInfo.mPosition--;
                span = getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
        } else {
            // choose the max span we can get. hopefully last one
            final int indexLimit = state.getItemCount() - 1;
            int pos = anchorInfo.mPosition;
            int bestSpan = span;
            while (pos < indexLimit) {
                int next = getSpanIndex(recycler, state, pos + 1);
                if (next > bestSpan) {
                    pos += 1;
                    bestSpan = next;
                } else {
                    break;
                }
            }
            anchorInfo.mPosition = pos;
        }
    }

    @Override
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state,
                            int start, int end, int itemCount) {
        ensureLayoutState();
        View invalidMatch = null;
        View outOfBoundsMatch = null;
        final int boundsStart = mOrientationHelper.getStartAfterPadding();
        final int boundsEnd = mOrientationHelper.getEndAfterPadding();
        final int diff = end > start ? 1 : -1;

        for (int i = start; i != end; i += diff) {
            final View view = getChildAt(i);
            final int position = getPosition(view);
            if (position >= 0 && position < itemCount) {
                final int span = getSpanIndex(recycler, state, position);
                if (span != 0) {
                    continue;
                }
                if (((RecyclerView.LayoutParams) view.getLayoutParams()).isItemRemoved()) {
                    if (invalidMatch == null) {
                        invalidMatch = view; // removed item, least preferred
                    }
                } else if (mOrientationHelper.getDecoratedStart(view) >= boundsEnd ||
                    mOrientationHelper.getDecoratedEnd(view) < boundsStart) {
                    if (outOfBoundsMatch == null) {
                        outOfBoundsMatch = view; // item is not visible, less preferred
                    }
                } else {
                    return view;
                }
            }
        }
        return outOfBoundsMatch != null ? outOfBoundsMatch : invalidMatch;
    }

    private int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state,
                                  int viewPosition) {
        if (!state.isPreLayout()) {
            return mSpanSizeLookup.getSpanGroupIndex(viewPosition, mSpanCount);
        }
        final int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(viewPosition);
        if (adapterPosition == -1) {
            if (DEBUG) {
                throw new RuntimeException("Cannot find span group index for position "
                    + viewPosition);
            }
            Log.w(TAG, "Cannot find span size for pre layout position. " + viewPosition);
            return 0;
        }
        return mSpanSizeLookup.getSpanGroupIndex(adapterPosition, mSpanCount);
    }

    private int getSpanIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int pos) {
        if (!state.isPreLayout()) {
            return mSpanSizeLookup.getCachedSpanIndex(pos, mSpanCount);
        }
        final int cached = mPreLayoutSpanIndexCache.get(pos, -1);
        if (cached != -1) {
            return cached;
        }
        final int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(pos);
        if (adapterPosition == -1) {
            if (DEBUG) {
                throw new RuntimeException("Cannot find span index for pre layout position. It is"
                    + " not cached, not in the adapter. Pos:" + pos);
            }
            Log.w(TAG, "Cannot find span size for pre layout position. It is"
                + " not cached, not in the adapter. Pos:" + pos);
            return 0;
        }
        return mSpanSizeLookup.getCachedSpanIndex(adapterPosition, mSpanCount);
    }

    private int getSpanSize(RecyclerView.Recycler recycler, RecyclerView.State state, int pos) {
        if (!state.isPreLayout()) {
            return mSpanSizeLookup.getSpanSize(pos);
        }
        final int cached = mPreLayoutSpanSizeCache.get(pos, -1);
        if (cached != -1) {
            return cached;
        }
        final int adapterPosition = recycler.convertPreLayoutPositionToPostLayout(pos);
        if (adapterPosition == -1) {
            if (DEBUG) {
                throw new RuntimeException("Cannot find span size for pre layout position. It is"
                    + " not cached, not in the adapter. Pos:" + pos);
            }
            Log.w(TAG, "Cannot find span size for pre layout position. It is"
                + " not cached, not in the adapter. Pos:" + pos);
            return 1;
        }
        return mSpanSizeLookup.getSpanSize(adapterPosition);
    }

    @Override
    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState,
                                                LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int remainingSpan = mSpanCount;
        int count = 0;
        while (count < mSpanCount && layoutState.hasMore(state) && remainingSpan > 0) {
            final int pos = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(pos, Math.max(0, layoutState.mScrollingOffset));
            final int spanSize = mSpanSizeLookup.getSpanSize(pos);
            remainingSpan -= spanSize;
            layoutState.mCurrentPosition += layoutState.mItemDirection;
            count++;
        }
    }

    @Override
    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state,
                     LayoutState layoutState, LayoutChunkResult result) {
        final int otherDirSpecMode = mOrientationHelper.getModeInOther();
        final boolean flexibleInOtherDir = otherDirSpecMode != View.MeasureSpec.EXACTLY;
        final int currentOtherDirSize = getChildCount() > 0 ? mCachedBorders[mSpanCount] : 0;
        // if grid layout's dimensions are not specified, let the new row change the measurements
        // This is not perfect since we not covering all rows but still solves an important case
        // where they may have a header row which should be laid out according to children.
        if (flexibleInOtherDir) {
            updateMeasurements(); //  reset measurements
        }
        final boolean layingOutInPrimaryDirection =
            layoutState.mItemDirection == LayoutState.ITEM_DIRECTION_TAIL;
        int count = 0;
        int consumedSpanCount = 0;
        int remainingSpan = mSpanCount;
        if (!layingOutInPrimaryDirection) {
            int itemSpanIndex = getSpanIndex(recycler, state, layoutState.mCurrentPosition);
            int itemSpanSize = getSpanSize(recycler, state, layoutState.mCurrentPosition);
            remainingSpan = itemSpanIndex + itemSpanSize;
        }
        while (count < mSpanCount && layoutState.hasMore(state) && remainingSpan > 0) {
            int pos = layoutState.mCurrentPosition;
            final int spanSize = getSpanSize(recycler, state, pos);
            if (spanSize > mSpanCount) {
                throw new IllegalArgumentException("Item at position " + pos + " requires " +
                    spanSize + " spans but GridLayoutManager has only " + mSpanCount
                    + " spans.");
            }
            remainingSpan -= spanSize;
            if (remainingSpan < 0) {
                break; // item did not fit into this row or column
            }
            View view = layoutState.next(recycler);
            if (view == null) {
                break;
            }
            consumedSpanCount += spanSize;
            mSet[count] = view;
            count++;
        }

        if (count == 0) {
            result.mFinished = true;
            return;
        }

        int maxSize = 0;
        float maxSizeInOther = 0; // use a float to get size per span

        // we should assign spans before item decor offsets are calculated
        assignSpans(recycler, state, count, consumedSpanCount, layingOutInPrimaryDirection);
        for (int i = 0; i < count; i++) {
            View view = mSet[i];
            if (layoutState.mScrapList == null) {
                if (layingOutInPrimaryDirection) {
                    addView(view);
                } else {
                    addView(view, 0);
                }
            } else {
                if (layingOutInPrimaryDirection) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
            }
            calculateItemDecorationsForChild(view, mDecorInsets);

            measureChild(view, otherDirSpecMode, false);
            final int size = mOrientationHelper.getDecoratedMeasurement(view);
            if (size > maxSize) {
                maxSize = size;
            }
            final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            final float otherSize = 1f * mOrientationHelper.getDecoratedMeasurementInOther(view) /
                lp.mSpanSize;
            if (otherSize > maxSizeInOther) {
                maxSizeInOther = otherSize;
            }
        }
        if (flexibleInOtherDir) {
            // re-distribute columns
            guessMeasurement(maxSizeInOther, currentOtherDirSize);
            // now we should re-measure any item that was match parent.
            maxSize = 0;
            for (int i = 0; i < count; i++) {
                View view = mSet[i];
                measureChild(view, View.MeasureSpec.EXACTLY, true);
                final int size = mOrientationHelper.getDecoratedMeasurement(view);
                if (size > maxSize) {
                    maxSize = size;
                }
            }
        }

        // Views that did not measure the maxSize has to be re-measured
        // We will stop doing this once we introduce Gravity in the GLM layout params
        for (int i = 0; i < count; i++) {
            final View view = mSet[i];
            if (mOrientationHelper.getDecoratedMeasurement(view) != maxSize) {
                final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                final Rect decorInsets = lp.mDecorInsets;
                final int verticalInsets = decorInsets.top + decorInsets.bottom
                    + lp.topMargin + lp.bottomMargin;
                final int horizontalInsets = decorInsets.left + decorInsets.right
                    + lp.leftMargin + lp.rightMargin;
                final int totalSpaceInOther = getSpaceForSpanRange(lp.mSpanIndex, lp.mSpanSize);
                final int wSpec;
                final int hSpec;
                if (mOrientation == VERTICAL) {
                    wSpec = getChildMeasureSpec(totalSpaceInOther, View.MeasureSpec.EXACTLY,
                        horizontalInsets, lp.width, false);
                    hSpec = View.MeasureSpec.makeMeasureSpec(maxSize - verticalInsets,
                        View.MeasureSpec.EXACTLY);
                } else {
                    wSpec = View.MeasureSpec.makeMeasureSpec(maxSize - horizontalInsets,
                        View.MeasureSpec.EXACTLY);
                    hSpec = getChildMeasureSpec(totalSpaceInOther, View.MeasureSpec.EXACTLY,
                        verticalInsets, lp.height, false);
                }
                measureChildWithDecorationsAndMargin(view, wSpec, hSpec, true);
            }
        }

        result.mConsumed = maxSize;

        int left = 0, right = 0, top = 0, bottom = 0;
        if (mOrientation == VERTICAL) {
            if (layoutState.mLayoutDirection == LayoutState.LAYOUT_START) {
                bottom = layoutState.mOffset;
                top = bottom - maxSize;
            } else {
                top = layoutState.mOffset;
                bottom = top + maxSize;
            }
        } else {
            if (layoutState.mLayoutDirection == LayoutState.LAYOUT_START) {
                right = layoutState.mOffset;
                left = right - maxSize;
            } else {
                left = layoutState.mOffset;
                right = left + maxSize;
            }
        }
        for (int i = 0; i < count; i++) {
            View view = mSet[i];
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            if (mOrientation == VERTICAL) {
                if (isLayoutRTL()) {
                    right = getPaddingLeft() + mCachedBorders[mSpanCount - params.mSpanIndex];
                    left = right - mOrientationHelper.getDecoratedMeasurementInOther(view);
                } else {
                    left = getPaddingLeft() + mCachedBorders[params.mSpanIndex];
                    right = left + mOrientationHelper.getDecoratedMeasurementInOther(view);
                }
            } else {
                top = getPaddingTop() + mCachedBorders[params.mSpanIndex];
                bottom = top + mOrientationHelper.getDecoratedMeasurementInOther(view);
            }
            // We calculate everything with View's bounding box (which includes decor and margins)
            // To calculate correct layout position, we subtract margins.
            layoutDecoratedWithMargins(view, left, top, right, bottom);
            if (DEBUG) {
                Log.d(TAG, "laid out child at position " + getPosition(view) + ", with l:"
                    + (left + params.leftMargin) + ", t:" + (top + params.topMargin) + ", r:"
                    + (right - params.rightMargin) + ", b:" + (bottom - params.bottomMargin)
                    + ", span:" + params.mSpanIndex + ", spanSize:" + params.mSpanSize);
            }
            // Consume the available space if the view is not removed OR changed
            if (params.isItemRemoved() || params.isItemChanged()) {
                result.mIgnoreConsumed = true;
            }
            result.mFocusable |= view.hasFocusable();
        }
        Arrays.fill(mSet, null);
    }

    /**
     * Measures a child with currently known information. This is not necessarily the child's final
     * measurement. (see fillChunk for details).
     *
     * @param view The child view to be measured
     * @param otherDirParentSpecMode The RV measure spec that should be used in the secondary
     * orientation
     * @param alreadyMeasured True if we've already measured this view once
     */
    private void measureChild(View view, int otherDirParentSpecMode, boolean alreadyMeasured) {
        final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        final Rect decorInsets = lp.mDecorInsets;
        final int verticalInsets = decorInsets.top + decorInsets.bottom
            + lp.topMargin + lp.bottomMargin;
        final int horizontalInsets = decorInsets.left + decorInsets.right
            + lp.leftMargin + lp.rightMargin;
        final int availableSpaceInOther = getSpaceForSpanRange(lp.mSpanIndex, lp.mSpanSize);
        final int wSpec;
        final int hSpec;
        if (mOrientation == VERTICAL) {
            wSpec = getChildMeasureSpec(availableSpaceInOther, otherDirParentSpecMode,
                horizontalInsets, lp.width, false);
            hSpec = getChildMeasureSpec(mOrientationHelper.getTotalSpace(), getHeightMode(),
                verticalInsets, lp.height, true);
        } else {
            hSpec = getChildMeasureSpec(availableSpaceInOther, otherDirParentSpecMode,
                verticalInsets, lp.height, false);
            wSpec = getChildMeasureSpec(mOrientationHelper.getTotalSpace(), getWidthMode(),
                horizontalInsets, lp.width, true);
        }
        measureChildWithDecorationsAndMargin(view, wSpec, hSpec, alreadyMeasured);
    }

    /**
     * This is called after laying out a row (if vertical) or a column (if horizontal) when the
     * RecyclerView does not have exact measurement specs.
     * <p>
     * Here we try to assign a best guess width or height and re-do the layout to update other
     * views that wanted to MATCH_PARENT in the non-scroll orientation.
     *
     * @param maxSizeInOther The maximum size per span ratio from the measurement of the children.
     * @param currentOtherDirSize The size before this layout chunk. There is no reason to go below.
     */
    private void guessMeasurement(float maxSizeInOther, int currentOtherDirSize) {
        final int contentSize = Math.round(maxSizeInOther * mSpanCount);
        // always re-calculate because borders were stretched during the fill
        calculateItemBorders(Math.max(contentSize, currentOtherDirSize));
    }

    private void measureChildWithDecorationsAndMargin(View child, int widthSpec, int heightSpec,
                                                      boolean alreadyMeasured) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        final boolean measure;
        if (alreadyMeasured) {
            measure = shouldReMeasureChild(child, widthSpec, heightSpec, lp);
        } else {
            measure = shouldMeasureChild(child, widthSpec, heightSpec, lp);
        }
        if (measure) {
            child.measure(widthSpec, heightSpec);
        }
    }

    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int count,
                             int consumedSpanCount, boolean layingOutInPrimaryDirection) {
        // spans are always assigned from 0 to N no matter if it is RTL or not.
        // RTL is used only when positioning the view.
        int span, start, end, diff;
        // make sure we traverse from min position to max position
        if (layingOutInPrimaryDirection) {
            start = 0;
            end = count;
            diff = 1;
        } else {
            start = count - 1;
            end = -1;
            diff = -1;
        }
        span = 0;
        for (int i = start; i != end; i += diff) {
            View view = mSet[i];
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            params.mSpanSize = getSpanSize(recycler, state, getPosition(view));
            params.mSpanIndex = span;
            span += params.mSpanSize;
        }
    }

    /**
     * Returns the number of spans laid out by this grid.
     *
     * @return The number of spans
     * @see #setSpanCount(int)
     */
    public int getSpanCount() {
        return mSpanCount;
    }

    /**
     * Sets the number of spans to be laid out.
     * <p>
     * If {@link #getOrientation()} is {@link #VERTICAL}, this is the number of columns.
     * If {@link #getOrientation()} is {@link #HORIZONTAL}, this is the number of rows.
     *
     * @param spanCount The total number of spans in the grid
     * @see #getSpanCount()
     */
    public void setSpanCount(int spanCount) {
        if (spanCount == mSpanCount) {
            return;
        }
        mPendingSpanCountChange = true;
        if (spanCount < 1) {
            throw new IllegalArgumentException("Span count should be at least 1. Provided "
                + spanCount);
        }
        mSpanCount = spanCount;
        mSpanSizeLookup.invalidateSpanIndexCache();
        requestLayout();
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection,
                                    RecyclerView.Recycler recycler, RecyclerView.State state) {
        View prevFocusedChild = findContainingItemView(focused);
        if (prevFocusedChild == null) {
            return null;
        }
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) prevFocusedChild.getLayoutParams();
        final int prevSpanStart = lp.mSpanIndex;
        final int prevSpanEnd = lp.mSpanIndex + lp.mSpanSize;
        View view = super.onFocusSearchFailed(focused, focusDirection, recycler, state);
        if (view == null) {
            return null;
        }
        // LinearLayoutManager finds the last child. What we want is the child which has the same
        // spanIndex.
        final int layoutDir = convertFocusDirectionToLayoutDirection(focusDirection);
        final boolean ascend = (layoutDir == LayoutState.LAYOUT_END) != mShouldReverseLayout;
        final int start, inc, limit;
        if (ascend) {
            start = getChildCount() - 1;
            inc = -1;
            limit = -1;
        } else {
            start = 0;
            inc = 1;
            limit = getChildCount();
        }
        final boolean preferLastSpan = mOrientation == VERTICAL && isLayoutRTL();

        // The focusable candidate to be picked if no perfect focusable candidate is found.
        // The best focusable candidate is the one with the highest amount of span overlap with
        // the currently focused view.
        View focusableWeakCandidate = null; // somewhat matches but not strong
        int focusableWeakCandidateSpanIndex = -1;
        int focusableWeakCandidateOverlap = 0; // how many spans overlap

        // The unfocusable candidate to become visible on the screen next, if no perfect or
        // weak focusable candidates are found to receive focus next.
        // We are only interested in partially visible unfocusable views. These are views that are
        // not fully visible, that is either partially overlapping, or out-of-bounds and right below
        // or above RV's padded bounded area. The best unfocusable candidate is the one with the
        // highest amount of span overlap with the currently focused view.
        View unfocusableWeakCandidate = null; // somewhat matches but not strong
        int unfocusableWeakCandidateSpanIndex = -1;
        int unfocusableWeakCandidateOverlap = 0; // how many spans overlap

        // The span group index of the start child. This indicates the span group index of the
        // next focusable item to receive focus, if a focusable item within the same span group
        // exists. Any focusable item beyond this group index are not relevant since they
        // were already stored in the layout before onFocusSearchFailed call and were not picked
        // by the focusSearch algorithm.
        int focusableSpanGroupIndex = getSpanGroupIndex(recycler, state, start);
        for (int i = start; i != limit; i += inc) {
            int spanGroupIndex = getSpanGroupIndex(recycler, state, i);
            View candidate = getChildAt(i);
            if (candidate == prevFocusedChild) {
                break;
            }

            if (candidate.hasFocusable() && spanGroupIndex != focusableSpanGroupIndex) {
                // We are past the allowable span group index for the next focusable item.
                // The search only continues if no focusable weak candidates have been found up
                // until this point, in order to find the best unfocusable candidate to become
                // visible on the screen next.
                if (focusableWeakCandidate != null) {
                    break;
                }
                continue;
            }

            final GridLayoutManager.LayoutParams candidateLp = (GridLayoutManager.LayoutParams) candidate.getLayoutParams();
            final int candidateStart = candidateLp.mSpanIndex;
            final int candidateEnd = candidateLp.mSpanIndex + candidateLp.mSpanSize;
            if (candidate.hasFocusable() && candidateStart == prevSpanStart
                && candidateEnd == prevSpanEnd) {
                return candidate; // perfect match
            }
            boolean assignAsWeek = false;
            if ((candidate.hasFocusable() && focusableWeakCandidate == null)
                || (!candidate.hasFocusable() && unfocusableWeakCandidate == null)) {
                assignAsWeek = true;
            } else {
                int maxStart = Math.max(candidateStart, prevSpanStart);
                int minEnd = Math.min(candidateEnd, prevSpanEnd);
                int overlap = minEnd - maxStart;
                if (candidate.hasFocusable()) {
                    if (overlap > focusableWeakCandidateOverlap) {
                        assignAsWeek = true;
                    } else if (overlap == focusableWeakCandidateOverlap
                        && preferLastSpan == (candidateStart
                        > focusableWeakCandidateSpanIndex)) {
                        assignAsWeek = true;
                    }
                } else if (focusableWeakCandidate == null
                    && isViewPartiallyVisible(candidate, false, true)) {
                    if (overlap > unfocusableWeakCandidateOverlap) {
                        assignAsWeek = true;
                    } else if (overlap == unfocusableWeakCandidateOverlap
                        && preferLastSpan == (candidateStart
                        > unfocusableWeakCandidateSpanIndex)) {
                        assignAsWeek = true;
                    }
                }
            }

            if (assignAsWeek) {
                if (candidate.hasFocusable()) {
                    focusableWeakCandidate = candidate;
                    focusableWeakCandidateSpanIndex = candidateLp.mSpanIndex;
                    focusableWeakCandidateOverlap = Math.min(candidateEnd, prevSpanEnd)
                        - Math.max(candidateStart, prevSpanStart);
                } else {
                    unfocusableWeakCandidate = candidate;
                    unfocusableWeakCandidateSpanIndex = candidateLp.mSpanIndex;
                    unfocusableWeakCandidateOverlap = Math.min(candidateEnd, prevSpanEnd)
                        - Math.max(candidateStart, prevSpanStart);
                }
            }
        }
        return (focusableWeakCandidate != null) ? focusableWeakCandidate : unfocusableWeakCandidate;
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return mPendingSavedState == null && !mPendingSpanCountChange;
    }

}
