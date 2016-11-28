package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * DrawerLayout without drawers. Has working handling of custom status bar background.
 *
 * Top level container for one child which adds a status bar scrim.
 */

public class SingleLayout extends ViewGroup implements SingleLayoutImpl {
    private static final String TAG = SingleLayout.class.getSimpleName();

    interface SingleLayoutCompatImpl {
        void configureApplyInsets(View drawerLayout);

        void dispatchChildInsets(View child, Object insets, int drawerGravity);

        void applyMarginInsets(MarginLayoutParams lp, Object insets, int drawerGravity);

        int getTopInset(Object lastInsets);

        Drawable getDefaultStatusBarBackground(Context context);
    }

    static class SingleLayoutCompatImplBase implements SingleLayoutCompatImpl {
        public void configureApplyInsets(View drawerLayout) {
            // This space for rent
        }

        public void dispatchChildInsets(View child, Object insets, int drawerGravity) {
            // This space for rent
        }

        public void applyMarginInsets(MarginLayoutParams lp, Object insets, int drawerGravity) {
            // This space for rent
        }

        public int getTopInset(Object insets) {
            return 0;
        }

        @Override
        public Drawable getDefaultStatusBarBackground(Context context) {
            return null;
        }
    }

    static class SingleLayoutCompatImplApi21 implements SingleLayoutCompatImpl {
        public void configureApplyInsets(View drawerLayout) {
            SingleLayoutCompatApi21.configureApplyInsets(drawerLayout);
        }

        public void dispatchChildInsets(View child, Object insets, int drawerGravity) {
            SingleLayoutCompatApi21.dispatchChildInsets(child, insets, drawerGravity);
        }

        public void applyMarginInsets(MarginLayoutParams lp, Object insets, int drawerGravity) {
            SingleLayoutCompatApi21.applyMarginInsets(lp, insets, drawerGravity);
        }

        public int getTopInset(Object insets) {
            return SingleLayoutCompatApi21.getTopInset(insets);
        }

        @Override
        public Drawable getDefaultStatusBarBackground(Context context) {
            return SingleLayoutCompatApi21.getDefaultStatusBarBackground(context);
        }
    }

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new SingleLayoutCompatImplApi21();
        } else {
            IMPL = new SingleLayoutCompatImplBase();
        }
    }

    static final SingleLayoutCompatImpl IMPL;

    private Drawable mStatusBarBackground;
    private Object mLastInsets;
    private boolean mDrawStatusBarBackground;

    private boolean mInLayout;

    public SingleLayout(Context context) {
        this(context, null);
    }

    public SingleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (ViewCompat.getFitsSystemWindows(this)) {
            IMPL.configureApplyInsets(this);
            mStatusBarBackground = IMPL.getDefaultStatusBarBackground(context);

            final TypedArray a = context.obtainStyledAttributes(attrs, android.support.design.R.styleable.ScrimInsetsFrameLayout, defStyleAttr, 0);
            if (a.hasValue(android.support.design.R.styleable.ScrimInsetsFrameLayout_insetForeground)) {
                mStatusBarBackground = a.getDrawable(android.support.design.R.styleable.ScrimInsetsFrameLayout_insetForeground);
            }
            a.recycle();
        }

    }

    /**
     * @hide Internal use only; called to apply window insets when configured
     * with fitsSystemWindows="true"
     */
    @Override
    public void setChildInsets(Object insets, boolean draw) {
        mLastInsets = insets;
        mDrawStatusBarBackground = draw;
        setWillNotDraw(!draw && getBackground() == null);
        requestLayout();
    }

    /**
     * Set a drawable to draw in the insets area for the status bar.
     * Note that this will only be activated if this DrawerLayout fitsSystemWindows.
     *
     * @param bg Background drawable to draw behind the status bar
     */
    public void setStatusBarBackground(Drawable bg) {
        mStatusBarBackground = bg;
        invalidate();
    }

    /**
     * Gets the drawable used to draw in the insets area for the status bar.
     *
     * @return The status bar background drawable, or null if none set
     */
    public Drawable getStatusBarBackgroundDrawable() {
        return mStatusBarBackground;
    }

    /**
     * Set a drawable to draw in the insets area for the status bar.
     * Note that this will only be activated if this DrawerLayout fitsSystemWindows.
     *
     * @param resId Resource id of a background drawable to draw behind the status bar
     */
    public void setStatusBarBackground(int resId) {
        mStatusBarBackground = resId != 0 ? ContextCompat.getDrawable(getContext(), resId) : null;
        invalidate();
    }

    /**
     * Set a drawable to draw in the insets area for the status bar.
     * Note that this will only be activated if this DrawerLayout fitsSystemWindows.
     *
     * @param color Color to use as a background drawable to draw behind the status bar
     * in 0xAARRGGBB format.
     */
    public void setStatusBarBackgroundColor(@ColorInt int color) {
        mStatusBarBackground = new ColorDrawable(color);
        invalidate();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams
            ? new LayoutParams((LayoutParams) p)
            : p instanceof MarginLayoutParams
            ? new LayoutParams((MarginLayoutParams) p)
            : new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams && super.checkLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            if (isInEditMode()) {
                // Don't crash the layout editor. Consume all of the space if specified
                // or pick a magic number from thin air otherwise.
                // TODO Better communication with tools of this bogus state.
                // It will crash on a real device.
                if (widthMode == MeasureSpec.AT_MOST) {
                    widthMode = MeasureSpec.EXACTLY;
                } else if (widthMode == MeasureSpec.UNSPECIFIED) {
                    widthMode = MeasureSpec.EXACTLY;
                    widthSize = 300;
                }
                if (heightMode == MeasureSpec.AT_MOST) {
                    heightMode = MeasureSpec.EXACTLY;
                } else if (heightMode == MeasureSpec.UNSPECIFIED) {
                    heightMode = MeasureSpec.EXACTLY;
                    heightSize = 300;
                }
            } else {
                throw new IllegalArgumentException(
                    "DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }

        setMeasuredDimension(widthSize, heightSize);

        final boolean applyInsets = mLastInsets != null && ViewCompat.getFitsSystemWindows(this);

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            if (applyInsets) {
                final int cgrav = Gravity.NO_GRAVITY;
                if (ViewCompat.getFitsSystemWindows(child)) {
                    IMPL.dispatchChildInsets(child, mLastInsets, cgrav);
                } else {
                    IMPL.applyMarginInsets(lp, mLastInsets, cgrav);
                }
            }

            // Content views get measured at exactly the layout's size.
            final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
            final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
            child.measure(contentWidthSpec, contentHeightSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mInLayout = true;

        for (int i = 0, count = getChildCount(); i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            child.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin + child.getMeasuredWidth(),
                lp.topMargin + child.getMeasuredHeight());
        }

        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (mDrawStatusBarBackground && mStatusBarBackground != null) {
            final int inset = IMPL.getTopInset(mLastInsets);
            if (inset > 0) {
                mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                mStatusBarBackground.draw(c);
            }
        }
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
