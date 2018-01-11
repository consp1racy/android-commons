/*
 * Copyright (C) 2016 The Android Open Source Project
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

package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * DividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of a {@link LinearLayoutManager}. It supports both {@link #HORIZONTAL} and
 * {@link #VERTICAL} orientations.
 * <p>
 * <pre>
 *     mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 *             mLayoutManager.getOrientation());
 *     recyclerView.addItemDecoration(mDividerItemDecoration);
 * </pre>
 */
public class XpDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    @IntDef(flag = true,
        value = {
            SHOW_DIVIDER_NONE,
            SHOW_DIVIDER_BEGINNING,
            SHOW_DIVIDER_MIDDLE,
            SHOW_DIVIDER_END
        })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {}

    /**
     * Don't show any dividers.
     */
    public static final int SHOW_DIVIDER_NONE = 0;
    /**
     * Show a divider at the beginning of the group.
     */
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    /**
     * Show dividers between each item in the group.
     */
    public static final int SHOW_DIVIDER_MIDDLE = 1 << 1;
    /**
     * Show a divider at the end of the group.
     */
    public static final int SHOW_DIVIDER_END = 1 << 2;

    private int mShowDividers;

    private Drawable mDivider;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    private boolean mDrawOver = false;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    @SuppressWarnings("RestrictedApi")
    public XpDividerItemDecoration(@NonNull Context context, int orientation) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, 0, ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    @NonNull
    public XpDividerItemDecoration drawOver(boolean drawOver) {
        mDrawOver = drawOver;
        return this;
    }

    public void setDrawOver(boolean drawOver) {
        mDrawOver = drawOver;
    }

    public boolean isDrawOver() {
        return mDrawOver;
    }

    /**
     * Set how dividers should be shown between items in this layout
     *
     * @param showDividers One or more of {@link #SHOW_DIVIDER_BEGINNING},
     * {@link #SHOW_DIVIDER_MIDDLE}, or {@link #SHOW_DIVIDER_END},
     * or {@link #SHOW_DIVIDER_NONE} to show no dividers.
     */
    @NonNull
    public XpDividerItemDecoration showDividers(@DividerMode int showDividers) {
        setShowDividers(showDividers);
        return this;
    }

    private void setShowDividers(final @DividerMode int showDividers) {
        mShowDividers = showDividers;
    }

    /**
     * @return A flag set indicating how dividers should be shown around items.
     * @see #showDividers(int)
     */
    @DividerMode
    public int getShowDividers() {
        return mShowDividers;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    @NonNull
    public XpDividerItemDecoration orientation(int orientation) {
        setOrientation(orientation);
        return this;
    }

    private void setOrientation(final int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    @NonNull
    public XpDividerItemDecoration drawable(@NonNull Drawable drawable) {
        setDrawable(drawable);
        return this;
    }

    private void setDrawable(final @NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        clipCanvasToPadding(canvas, parent);

        final int childCount = parent.getChildCount();
        if ((mShowDividers & SHOW_DIVIDER_BEGINNING) == SHOW_DIVIDER_BEGINNING) {
            final View firstChild = parent.getChildAt(0);
            if (firstChild != null) {
                drawVerticalAbove(canvas, parent, firstChild);
            }
        }
        if ((mShowDividers & SHOW_DIVIDER_END) == SHOW_DIVIDER_END) {
            final View lastChild = parent.getChildAt(childCount - 1);
            if (lastChild != null) {
                drawVerticalAtBottomOf(canvas, parent, lastChild);
            }
        }
        if ((mShowDividers & SHOW_DIVIDER_MIDDLE) == SHOW_DIVIDER_MIDDLE) {
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                drawVerticalAtBottomOf(canvas, parent, child);
            }
        }

        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void clipCanvasToPadding(@NonNull final Canvas canvas, @NonNull final RecyclerView parent) {
        if (parent.getClipToPadding()) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(left, top, right, bottom);
        }
    }

    private void drawVerticalAtBottomOf(@NonNull final Canvas canvas, @NonNull final RecyclerView parent, @NonNull final View child) {
        getChildBounds(parent, child);
        final int left = mBounds.left + Math.round(ViewCompat.getTranslationX(child));
        final int right = left + child.getWidth();
        final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
        final int top = bottom - mDivider.getIntrinsicHeight();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

    private void drawVerticalAbove(@NonNull final Canvas canvas, @NonNull final RecyclerView parent, @NonNull final View child) {
        getChildBounds(parent, child);
        final int left = mBounds.left + Math.round(child.getTranslationX());
        final int right = left + child.getWidth();
        final int bottom = mBounds.top + Math.round(child.getTranslationY());
        final int top = bottom - mDivider.getIntrinsicHeight();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

    private void drawHorizontal(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        clipCanvasToPadding(canvas, parent);

        final int childCount = parent.getChildCount();
        if ((mShowDividers & SHOW_DIVIDER_BEGINNING) == SHOW_DIVIDER_BEGINNING) {
            final View firstChild = parent.getChildAt(0);
            if (firstChild != null) {
                drawHorizontalToLeftOf(canvas, parent, firstChild);
            }
        }
        if ((mShowDividers & SHOW_DIVIDER_END) == SHOW_DIVIDER_END) {
            final View lastChild = parent.getChildAt(childCount - 1);
            if (lastChild != null) {
                drawHorizontalAtRightOf(canvas, parent, lastChild);
            }
        }
        if ((mShowDividers & SHOW_DIVIDER_MIDDLE) == SHOW_DIVIDER_MIDDLE) {
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                drawHorizontalAtRightOf(canvas, parent, child);
            }
        }
        canvas.restore();
    }

    private void drawHorizontalAtRightOf(@NonNull final Canvas canvas, @NonNull final RecyclerView parent, @NonNull final View child) {
        getChildBounds(parent, child);
        final int top = mBounds.top + Math.round(ViewCompat.getTranslationY(child));
        final int bottom = top + child.getHeight();
        final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
        final int left = right - mDivider.getIntrinsicWidth();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

    private void drawHorizontalToLeftOf(@NonNull final Canvas canvas, @NonNull final RecyclerView parent, @NonNull final View child) {
        getChildBounds(parent, child);
        final int top = mBounds.top + Math.round(ViewCompat.getTranslationY(child));
        final int bottom = top + child.getHeight();
        final int right = mBounds.left + Math.round(ViewCompat.getTranslationX(child));
        final int left = right - mDivider.getIntrinsicWidth();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

    private void getChildBounds(@NonNull final RecyclerView parent, @NonNull final View child) {
//        parent.getDecoratedBoundsWithMargins(child, mBounds);
//        parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
        mBounds.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                               RecyclerView.State state) {
//        final boolean showMiddle = (mShowDividers & SHOW_DIVIDER_MIDDLE) == SHOW_DIVIDER_MIDDLE;
//        if (showMiddle) {
//            if (mOrientation == VERTICAL) {
//                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//            } else {
//                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//            }
//        } else {
//            outRect.setEmpty();
//        }
//    }
}
