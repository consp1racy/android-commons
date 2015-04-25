package net.xpece.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

/**
 * Created by Eugen on 24. 3. 2015.
 */
@Deprecated
public class LayoutManagerWrapper<T extends RecyclerView.LayoutManager> extends RecyclerView.LayoutManager {

    final T mManager;

    protected LayoutManagerWrapper(T manager) {
        mManager = manager;
    }

    public final T getLayoutManager() {
        return mManager;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return mManager.generateDefaultLayoutParams();
    }

    @Override
    public void requestLayout() {
        mManager.requestLayout();
    }

    @Override
    public void assertInLayoutOrScroll(String message) {
        mManager.assertInLayoutOrScroll(message);
    }

    @Override
    public void assertNotInLayoutOrScroll(String message) {
        mManager.assertNotInLayoutOrScroll(message);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return mManager.supportsPredictiveItemAnimations();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        mManager.onAttachedToWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view) {
        mManager.onDetachedFromWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        mManager.onDetachedFromWindow(view, recycler);
    }

    @Override
    public boolean getClipToPadding() {
        return mManager.getClipToPadding();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        mManager.onLayoutChildren(recycler, state);
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return mManager.checkLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return mManager.generateLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return mManager.generateLayoutParams(c, attrs);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollHorizontally() {
        return mManager.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return mManager.canScrollVertically();
    }

    @Override
    public void scrollToPosition(int position) {
        mManager.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        mManager.smoothScrollToPosition(recyclerView, state, position);
    }

    @Override
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        mManager.startSmoothScroll(smoothScroller);
    }

    @Override
    public boolean isSmoothScrolling() {
        return mManager.isSmoothScrolling();
    }

    @Override
    public int getLayoutDirection() {
        return mManager.getLayoutDirection();
    }

    @Override
    public void endAnimation(View view) {
        mManager.endAnimation(view);
    }

    @Override
    public void addDisappearingView(View child) {
        mManager.addDisappearingView(child);
    }

    @Override
    public void addDisappearingView(View child, int index) {
        mManager.addDisappearingView(child, index);
    }

    @Override
    public void addView(View child) {
        mManager.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        mManager.addView(child, index);
    }

    @Override
    public void removeView(View child) {
        mManager.removeView(child);
    }

    @Override
    public void removeViewAt(int index) {
        mManager.removeViewAt(index);
    }

    @Override
    public void removeAllViews() {
        mManager.removeAllViews();
    }

    @Override
    public int getPosition(View view) {
        return mManager.getPosition(view);
    }

    @Override
    public int getItemViewType(View view) {
        return mManager.getItemViewType(view);
    }

    @Override
    public View findViewByPosition(int position) {
        return mManager.findViewByPosition(position);
    }

    @Override
    public void detachView(View child) {
        mManager.detachView(child);
    }

    @Override
    public void detachViewAt(int index) {
        mManager.detachViewAt(index);
    }

    @Override
    public void attachView(View child, int index, RecyclerView.LayoutParams lp) {
        mManager.attachView(child, index, lp);
    }

    @Override
    public void attachView(View child, int index) {
        mManager.attachView(child, index);
    }

    @Override
    public void attachView(View child) {
        mManager.attachView(child);
    }

    @Override
    public void removeDetachedView(View child) {
        mManager.removeDetachedView(child);
    }

    @Override
    public void moveView(int fromIndex, int toIndex) {
        mManager.moveView(fromIndex, toIndex);
    }

    @Override
    public void detachAndScrapView(View child, RecyclerView.Recycler recycler) {
        mManager.detachAndScrapView(child, recycler);
    }

    @Override
    public void detachAndScrapViewAt(int index, RecyclerView.Recycler recycler) {
        mManager.detachAndScrapViewAt(index, recycler);
    }

    @Override
    public void removeAndRecycleView(View child, RecyclerView.Recycler recycler) {
        mManager.removeAndRecycleView(child, recycler);
    }

    @Override
    public void removeAndRecycleViewAt(int index, RecyclerView.Recycler recycler) {
        mManager.removeAndRecycleViewAt(index, recycler);
    }

    @Override
    public int getChildCount() {
        return mManager.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        return mManager.getChildAt(index);
    }

    @Override
    public int getWidth() {
        return mManager.getWidth();
    }

    @Override
    public int getHeight() {
        return mManager.getHeight();
    }

    @Override
    public int getPaddingLeft() {
        return mManager.getPaddingLeft();
    }

    @Override
    public int getPaddingTop() {
        return mManager.getPaddingTop();
    }

    @Override
    public int getPaddingRight() {
        return mManager.getPaddingRight();
    }

    @Override
    public int getPaddingBottom() {
        return mManager.getPaddingBottom();
    }

    @Override
    public int getPaddingStart() {
        return mManager.getPaddingStart();
    }

    @Override
    public int getPaddingEnd() {
        return mManager.getPaddingEnd();
    }

    @Override
    public boolean isFocused() {
        return mManager.isFocused();
    }

    @Override
    public boolean hasFocus() {
        return mManager.hasFocus();
    }

    @Override
    public View getFocusedChild() {
        return mManager.getFocusedChild();
    }

    @Override
    public int getItemCount() {
        return mManager.getItemCount();
    }

    @Override
    public void offsetChildrenHorizontal(int dx) {
        mManager.offsetChildrenHorizontal(dx);
    }

    @Override
    public void offsetChildrenVertical(int dy) {
        mManager.offsetChildrenVertical(dy);
    }

    @Override
    public void ignoreView(View view) {
        mManager.ignoreView(view);
    }

    @Override
    public void stopIgnoringView(View view) {
        mManager.stopIgnoringView(view);
    }

    @Override
    public void detachAndScrapAttachedViews(RecyclerView.Recycler recycler) {
        mManager.detachAndScrapAttachedViews(recycler);
    }

    @Override
    public void measureChild(View child, int widthUsed, int heightUsed) {
        mManager.measureChild(child, widthUsed, heightUsed);
    }

    @Override
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        mManager.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public int getDecoratedMeasuredWidth(View child) {
        return mManager.getDecoratedMeasuredWidth(child);
    }

    @Override
    public int getDecoratedMeasuredHeight(View child) {
        return mManager.getDecoratedMeasuredHeight(child);
    }

    @Override
    public void layoutDecorated(View child, int left, int top, int right, int bottom) {
        mManager.layoutDecorated(child, left, top, right, bottom);
    }

    @Override
    public int getDecoratedLeft(View child) {
        return mManager.getDecoratedLeft(child);
    }

    @Override
    public int getDecoratedTop(View child) {
        return mManager.getDecoratedTop(child);
    }

    @Override
    public int getDecoratedRight(View child) {
        return mManager.getDecoratedRight(child);
    }

    @Override
    public int getDecoratedBottom(View child) {
        return mManager.getDecoratedBottom(child);
    }

    @Override
    public void calculateItemDecorationsForChild(View child, Rect outRect) {
        mManager.calculateItemDecorationsForChild(child, outRect);
    }

    @Override
    public int getTopDecorationHeight(View child) {
        return mManager.getTopDecorationHeight(child);
    }

    @Override
    public int getBottomDecorationHeight(View child) {
        return mManager.getBottomDecorationHeight(child);
    }

    @Override
    public int getLeftDecorationWidth(View child) {
        return mManager.getLeftDecorationWidth(child);
    }

    @Override
    public int getRightDecorationWidth(View child) {
        return mManager.getRightDecorationWidth(child);
    }

    @Override
    public View onFocusSearchFailed(View focused, int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.onFocusSearchFailed(focused, direction, recycler, state);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        return mManager.onInterceptFocusSearch(focused, direction);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return mManager.requestChildRectangleOnScreen(parent, child, rect, immediate);
    }

    @Override
    public boolean onRequestChildFocus(RecyclerView parent, View child, View focused) {
        return mManager.onRequestChildFocus(parent, child, focused);
    }

    @Override
    public boolean onRequestChildFocus(RecyclerView parent, RecyclerView.State state, View child, View focused) {
        return mManager.onRequestChildFocus(parent, state, child, focused);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        mManager.onAdapterChanged(oldAdapter, newAdapter);
    }

    @Override
    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> views, int direction, int focusableMode) {
        return mManager.onAddFocusables(recyclerView, views, direction, focusableMode);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        mManager.onItemsChanged(recyclerView);
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        mManager.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        mManager.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) {
        mManager.onItemsUpdated(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        mManager.onItemsMoved(recyclerView, from, to, itemCount);
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return mManager.computeHorizontalScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return mManager.computeHorizontalScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return mManager.computeHorizontalScrollRange(state);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return mManager.computeVerticalScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return mManager.computeVerticalScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return mManager.computeVerticalScrollRange(state);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        mManager.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public void setMeasuredDimension(int widthSize, int heightSize) {
        mManager.setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public int getMinimumWidth() {
        return mManager.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return mManager.getMinimumHeight();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return mManager.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        mManager.onRestoreInstanceState(state);
    }

    @Override
    public void onScrollStateChanged(int state) {
        mManager.onScrollStateChanged(state);
    }

    @Override
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        mManager.removeAndRecycleAllViews(recycler);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat info) {
        mManager.onInitializeAccessibilityNodeInfo(recycler, state, info);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        mManager.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityEvent event) {
        mManager.onInitializeAccessibilityEvent(recycler, state, event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View host, AccessibilityNodeInfoCompat info) {
        mManager.onInitializeAccessibilityNodeInfoForItem(recycler, state, host, info);
    }

    @Override
    public void requestSimpleAnimationsInNextLayout() {
        mManager.requestSimpleAnimationsInNextLayout();
    }

    @Override
    public int getSelectionModeForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.getSelectionModeForAccessibility(recycler, state);
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.getRowCountForAccessibility(recycler, state);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.getColumnCountForAccessibility(recycler, state);
    }

    @Override
    public boolean isLayoutHierarchical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return mManager.isLayoutHierarchical(recycler, state);
    }

    @Override
    public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int action, Bundle args) {
        return mManager.performAccessibilityAction(recycler, state, action, args);
    }

    @Override
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, int action, Bundle args) {
        return mManager.performAccessibilityActionForItem(recycler, state, view, action, args);
    }
}
