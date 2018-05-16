/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.support.design.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.XpAppCompatCompoundDrawableHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import net.xpece.android.widget.TintableCompoundDrawableView;
import net.xpece.android.widget.cardbutton.R;

public class CardButton extends AppCompatButton implements TintableCompoundDrawableView {
    public static boolean AUTO_VISUAL_MARGIN_ENABLED = true;

    private static final String TAG = "CardButton";

    private Boolean mDebuggable = null;

    @SuppressWarnings("unused")
    public static void setVisualMargin(final CardButton cardButton, final int left, final int top, final int right, final int bottom) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            lp.leftMargin = left - Math.max(cardButton.getShadowPaddingLeft(), cardButton.getContentInsetLeft());
            lp.topMargin = top - Math.max(cardButton.getShadowPaddingTop(), cardButton.getContentInsetTop());
            lp.rightMargin = right - Math.max(cardButton.getShadowPaddingRight(), cardButton.getContentInsetRight());
            lp.bottomMargin = bottom - Math.max(cardButton.getShadowPaddingBottom(), cardButton.getContentInsetBottom());
        } catch (ClassCastException ex) {
            Log.e(TAG, "Margins are not supported.");
        }
    }

    @SuppressWarnings("unused")
    public static void setVisualMarginOriginal(final CardButton cardButton) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            lp.leftMargin -= Math.max(cardButton.getShadowPaddingLeft(), cardButton.getContentInsetLeft());
            lp.topMargin -= Math.max(cardButton.getShadowPaddingTop(), cardButton.getContentInsetTop());
            lp.rightMargin -= Math.max(cardButton.getShadowPaddingRight(), cardButton.getContentInsetRight());
            lp.bottomMargin -= Math.max(cardButton.getShadowPaddingBottom(), cardButton.getContentInsetBottom());
        } catch (ClassCastException ex) {
            Log.e(TAG, "Margins are not supported.");
        }
    }

    @SuppressWarnings("unused")
    public static void setVisualMarginRelative(final CardButton cardButton, final int start, final int top, final int end, final int bottom) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(lp, start - Math.max(cardButton.getShadowPaddingStart(), cardButton.getContentInsetStart()));
            lp.topMargin = top - Math.max(cardButton.getShadowPaddingTop(), cardButton.getContentInsetTop());
            MarginLayoutParamsCompat.setMarginEnd(lp, end - Math.max(cardButton.getShadowPaddingEnd(), cardButton.getContentInsetEnd()));
            lp.bottomMargin = bottom - Math.max(cardButton.getShadowPaddingBottom(), cardButton.getContentInsetBottom());
        } catch (ClassCastException ex) {
            Log.e(TAG, "Margins are not supported.");
        }
    }

    @SuppressWarnings("unused")
    public static void setVisualMarginRelativeOriginal(final CardButton cardButton) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(lp, MarginLayoutParamsCompat.getMarginStart(lp) - Math.max(cardButton.getShadowPaddingStart(), cardButton.getContentInsetStart()));
            lp.topMargin -= Math.max(cardButton.getShadowPaddingTop(), cardButton.getContentInsetTop());
            MarginLayoutParamsCompat.setMarginEnd(lp, MarginLayoutParamsCompat.getMarginEnd(lp) - Math.max(cardButton.getShadowPaddingEnd(), cardButton.getContentInsetEnd()));
            lp.bottomMargin -= Math.max(cardButton.getShadowPaddingBottom(), cardButton.getContentInsetBottom());
        } catch (ClassCastException ex) {
            Log.e(TAG, "Margins are not supported.");
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        mEatRequestLayout = true;
        super.setLayoutParams(params);
        if (AUTO_VISUAL_MARGIN_ENABLED) {
            try {
                if (params instanceof RecyclerView.LayoutParams) {
                    throwUnsupportedVisualMarginLayoutParams(params);
                } else if (params instanceof ViewGroup.MarginLayoutParams) {
                    final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) params;
                    if (MarginLayoutParamsCompat.isMarginRelative(lp)) {
                        setVisualMarginRelativeOriginal(this);
                    } else {
                        setVisualMarginOriginal(this);
                    }
                } else {
                    throwUnsupportedVisualMarginLayoutParams(params);
                }
            } catch (UnsupportedOperationException ex) {
                if (isDebuggable()) {
                    Log.w(TAG, this + " cannot automatically alter visual margins.", ex);
                }
            }
        }
        mEatRequestLayout = false;
        requestLayout();
    }

    private void throwUnsupportedVisualMarginLayoutParams(final ViewGroup.LayoutParams params) {
        throw new UnsupportedOperationException(params.getClass().getCanonicalName() + " does not support altering visual margins.");
    }

    private boolean isDebuggable() {
        if (mDebuggable == null) {
            final Context context = getContext();
            final ApplicationInfo appInfo = context.getApplicationInfo();
            mDebuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        }
        return mDebuggable || Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    @Override
    public void requestLayout() {
        if (!mEatRequestLayout) {
            super.requestLayout();
        }
    }

    @Override
    public void invalidate() {
        if (!mEatInvalidate) {
            super.invalidate();
        }
    }

    boolean mEatRequestLayout = false;
    boolean mEatInvalidate = false;

    @Nullable private ColorStateList mBackgroundTint;
    @Nullable private PorterDuff.Mode mBackgroundTintMode;

    @IntRange(from = 0) private int mBorderWidth;
    @ColorInt private int mRippleColor;

    @FloatRange(from = 0) float mCornerRadius;
    final Rect mContentPadding = new Rect();
    @IntRange(from = 0) private int mContentMinHeight;
    @IntRange(from = 0) private int mContentMinWidth;
    @Nullable private ColorStateList mBorderColor;

    final Rect mContentInset = new Rect();

    private XpAppCompatCompoundDrawableHelper mTextCompoundDrawableHelper;

    boolean mCompatPadding;
    final Rect mShadowPadding = new Rect();
    private final Rect mTouchArea = new Rect();

    private CardButtonImpl mImpl;
    private boolean mSuperInit = false;

    private Drawable mForeground;
    boolean mForegroundBoundsChanged;
    private final Rect mForegroundBounds = new Rect();

    private Drawable mBackgroundPrototype;

    boolean mDrawSelectorOnTop = true; // Backwards compatibility requires true.

    public CardButton(Context context) {
        this(context, null);
    }

    public CardButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, 0, 0);
    }

    @SuppressWarnings({"RestrictedApi", "SameParameterValue"})
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // Sincerely, fuck you, AppCompatBackgroundHelper.applyFrameworkTintUsingColorFilter().
        // Remove on all platforms for consistency. And we handle background ourselves.
        CardButtonReflection.removeAppCompatBackgroundHelper(this);

        mSuperInit = true;

        CardButtonUtils.checkAppCompatTheme(context);

        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
            R.styleable.CardButton, defStyleAttr, defStyleRes);
        mBackgroundPrototype = a.getDrawable(R.styleable.CardButton_android_background);
        mBackgroundTint = a.getColorStateList(R.styleable.CardButton_backgroundTint);
        mBackgroundTintMode = CardButtonUtils.parseTintMode(a.getInt(
            R.styleable.CardButton_backgroundTintMode, -1), null);
        mRippleColor = a.getColor(R.styleable.CardButton_rippleColor, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CardButton_borderWidth, 0);
        final float elevation = a.getDimension(R.styleable.CardButton_elevation, 0f);
        final float pressedTranslationZ = a.getDimension(
            R.styleable.CardButton_pressedTranslationZ, 0f);
        mCompatPadding = a.getBoolean(R.styleable.CardButton_useCompatPadding, false);
        mDrawSelectorOnTop = a.getBoolean(R.styleable.CardButton_android_drawSelectorOnTop, mDrawSelectorOnTop);

        mCornerRadius = a.getDimension(R.styleable.CardButton_carb_cornerRadius, 0f);
        int defaultPadding = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentPadding, 0);
        mContentPadding.left = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentPaddingLeft, defaultPadding);
        mContentPadding.top = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentPaddingTop, defaultPadding);
        mContentPadding.right = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentPaddingRight, defaultPadding);
        mContentPadding.bottom = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentPaddingBottom, defaultPadding);
        mContentMinWidth = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentMinWidth, 0);
        mContentMinHeight = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentMinHeight, 0);
        mBorderColor = a.getColorStateList(R.styleable.CardButton_carb_borderColor);

        mContentInset.left = a.getDimensionPixelOffset(R.styleable.CardButton_carb_insetLeft, 0);
        mContentInset.top = a.getDimensionPixelOffset(R.styleable.CardButton_carb_insetTop, 0);
        mContentInset.right = a.getDimensionPixelOffset(R.styleable.CardButton_carb_insetRight, 0);
        mContentInset.bottom = a.getDimensionPixelOffset(R.styleable.CardButton_carb_insetBottom, 0);
        a.recycle();

        updateBackgroundDrawable();
        getImpl().setElevation(elevation);
        getImpl().setPressedTranslationZ(pressedTranslationZ);

        updateMinSize();

        mTextCompoundDrawableHelper = new XpAppCompatCompoundDrawableHelper(this);
        mTextCompoundDrawableHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    void updateMinSize() {
        super.setMinWidth(getEffectiveInsetLeft() + getEffectiveInsetRight() + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        super.setMinHeight(getEffectiveInsetTop() + getEffectiveInsetBottom() + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
    }

    int getEffectiveInsetLeft() {return Math.max(mShadowPadding.left, mContentInset.left);}

    int getEffectiveInsetTop() {return Math.max(mShadowPadding.top, mContentInset.top);}

    int getEffectiveInsetRight() {return Math.max(mShadowPadding.right, mContentInset.right);}

    int getEffectiveInsetBottom() {return Math.max(mShadowPadding.bottom, mContentInset.bottom);}

    @SuppressWarnings("unused")
    public int getContentInsetLeft() { return mContentInset.left; }

    @SuppressWarnings("unused")
    public int getContentInsetRight() { return mContentInset.right; }

    @SuppressWarnings("unused")
    public int getContentInsetTop() { return mContentInset.top; }

    @SuppressWarnings("unused")
    public int getContentInsetBottom() { return mContentInset.bottom; }

    @SuppressWarnings("unused")
    public int getContentInsetEnd() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentInset.left;
        } else {
            return mContentInset.right;
        }
    }

    @SuppressWarnings("unused")
    public int getContentInsetStart() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentInset.right;
        } else {
            return mContentInset.left;
        }
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingLeft() {
        return mShadowPadding.left;
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingRight() {
        return mShadowPadding.right;
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingEnd() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mShadowPadding.left;
        } else {
            return mShadowPadding.right;
        }
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingStart() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mShadowPadding.right;
        } else {
            return mShadowPadding.left;
        }
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingTop() {
        return mShadowPadding.top;
    }

    @SuppressWarnings("unused")
    public int getShadowPaddingBottom() {
        return mShadowPadding.bottom;
    }

    @Override
    public void setMinWidth(int minWidth) {
        if (!mSuperInit) return;

        if (mContentMinWidth != minWidth) {
            mContentMinWidth = minWidth;
            super.setMinWidth(getEffectiveInsetLeft() + getEffectiveInsetRight() + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        }
    }

    @Override
    public void setMinHeight(int minHeight) {
        if (!mSuperInit) return;

        if (mContentMinHeight != minHeight) {
            mContentMinHeight = minHeight;
            super.setMinHeight(getEffectiveInsetTop() + getEffectiveInsetBottom() + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
        }
    }

    @Override
    public void setMinimumWidth(int minWidth) {
        if (!mSuperInit) return;

        if (mContentMinWidth != minWidth) {
            mContentMinWidth = minWidth;
            super.setMinimumWidth(getEffectiveInsetLeft() + getEffectiveInsetRight() + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        }
    }

    @Override
    public void setMinimumHeight(int minHeight) {
        if (!mSuperInit) return;

        if (mContentMinHeight != minHeight) {
            mContentMinHeight = minHeight;
            super.setMinimumHeight(getEffectiveInsetTop() + getEffectiveInsetBottom() + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
        }
    }

    @SuppressWarnings("unused")
    public void setContentPadding(int left, int top, int right, int bottom) {
        mContentPadding.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    @SuppressWarnings("unused")
    public void setContentPaddingRelative(int start, int top, int end, int bottom) {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        final int left = rtl ? end : start;
        final int right = rtl ? start : end;
        mContentPadding.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    @SuppressWarnings("unused")
    public void setContentInset(int left, int top, int right, int bottom) {
        mContentInset.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    @SuppressWarnings("unused")
    public void setContentInsetRelative(int start, int top, int end, int bottom) {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        final int left = rtl ? end : start;
        final int right = rtl ? start : end;
        mContentInset.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    @SuppressWarnings("unused")
    public int getContentPaddingLeft() {
        return mContentPadding.left;
    }

    @SuppressWarnings("unused")
    public int getContentPaddingRight() {
        return mContentPadding.right;
    }

    @SuppressWarnings("unused")
    public int getContentPaddingEnd() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentPadding.left;
        } else {
            return mContentPadding.right;
        }
    }

    @SuppressWarnings("unused")
    public int getContentPaddingStart() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentPadding.right;
        } else {
            return mContentPadding.left;
        }
    }

    @SuppressWarnings("unused")
    public int getContentPaddingTop() {
        return mContentPadding.top;
    }

    @SuppressWarnings("unused")
    public int getContentPaddingBottom() {
        return mContentPadding.bottom;
    }

    @SuppressWarnings("unused")
    public void setCornerRadius(float cornerRadius) {
        if (mCornerRadius != cornerRadius) {
            mCornerRadius = cornerRadius;
            updateBackgroundDrawable();
        }
    }

    private void updateBackgroundDrawable() {
        getImpl().setBackgroundDrawable(mBackgroundPrototype, mBackgroundTint, mBackgroundTintMode,
            mRippleColor, mBorderWidth,
            mBorderColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getImpl().updatePadding();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Returns the ripple color for this button.
     *
     * @return the ARGB color used for the ripple
     * @see #setRippleColor(int)
     */
    @ColorInt
    @SuppressWarnings("unused")
    public int getRippleColor() {
        return mRippleColor;
    }

    /**
     * Sets the ripple color for this button.
     * <p>
     * <p>When running on devices with KitKat or below, we draw this color as a filled circle
     * rather than a ripple.</p>
     *
     * @param color ARGB color to use for the ripple
     * @see #getRippleColor()
     */
    @SuppressWarnings("unused")
    public void setRippleColor(@ColorInt int color) {
        if (mRippleColor != color) {
            mRippleColor = color;
            getImpl().setRippleColor(color);
        }
    }

    /**
     * Returns the tint applied to the background drawable, if specified.
     *
     * @return the tint applied to the background drawable
     * @see #setBackgroundTintList(ColorStateList)
     */
    @Nullable
    @Override
    public ColorStateList getBackgroundTintList() {
        return mBackgroundTint;
    }

    /**
     * Applies a tint to the background drawable. Does not modify the current tint
     * mode, which is {@link PorterDuff.Mode#SRC_IN} by default.
     *
     * @param tint the tint to apply, may be {@code null} to clear tint
     */
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (mBackgroundTint != tint) {
            mBackgroundTint = tint;
            getImpl().setBackgroundTintList(tint);
        }
    }

    /**
     * Returns the blending mode used to apply the tint to the background
     * drawable, if specified.
     *
     * @return the blending mode used to apply the tint to the background
     * drawable
     * @see #setBackgroundTintMode(PorterDuff.Mode)
     */
    @Nullable
    @Override
    public PorterDuff.Mode getBackgroundTintMode() {
        return mBackgroundTintMode;
    }

    /**
     * Specifies the blending mode used to apply the tint specified by
     * {@link #setBackgroundTintList(ColorStateList)}} to the background
     * drawable. The default mode is {@link PorterDuff.Mode#SRC_IN}.
     *
     * @param tintMode the blending mode used to apply the tint, may be
     * {@code null} to clear tint
     */
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mBackgroundTintMode != tintMode) {
            mBackgroundTintMode = tintMode;
            getImpl().setBackgroundTintMode(tintMode);
        }
    }

    public void setBorderWidth(@IntRange(from = 0) int borderWidth) {
        if (mBorderWidth != borderWidth) {
            mBorderWidth = borderWidth;
            updateBackgroundDrawable();
        }
    }

    @SuppressWarnings("unused")
    public void setBorderColor(@Nullable ColorStateList borderColor) {
        if (mBorderColor != borderColor) {
            mBorderColor = borderColor;
            if (mBorderWidth > 0) {
                updateBackgroundDrawable();
            }
        }
    }

    @Nullable
    @SuppressWarnings("unused")
    public ColorStateList getBorderColor() {
        return mBorderColor;
    }

    @IntRange(from = 0)
    public int getBorderWidth() {
        return mBorderWidth;
    }

    @SuppressWarnings("deprecation")
    void superSetBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        Log.i(TAG, "Setting a custom background is not supported.");
    }

    @Override
    public void setBackgroundResource(int resid) {
        Log.i(TAG, "Setting a custom background is not supported.");
    }

    @Override
    public void setBackgroundColor(int color) {
        Log.i(TAG, "Setting a custom background is not supported.");
    }

    @Override
    @RequiresApi(23)
    @TargetApi(23)
    public void setForeground(Drawable foreground) {
        Log.i(TAG, "Setting a custom foreground is not supported.");
    }

    @Override
    @RequiresApi(23)
    @TargetApi(23)
    public void setForegroundGravity(int gravity) {
        Log.i(TAG, "Setting a custom foreground is not supported.");
    }

    @Override
    @RequiresApi(23)
    @TargetApi(23)
    public void setForegroundTintList(@Nullable ColorStateList tint) {
        Log.i(TAG, "Setting a custom foreground is not supported.");
    }

    @Override
    @RequiresApi(23)
    @TargetApi(23)
    public void setForegroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        Log.i(TAG, "Setting a custom foreground is not supported.");
    }

    void setForegroundCompat(@Nullable Drawable drawable) {
        if (mForeground != drawable) {
            if (mForeground != null) {
                mForeground.setCallback(null);
                unscheduleDrawable(mForeground);
            }

            mForeground = drawable;

            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    /**
     * Set whether CardButton should add inner padding on platforms Lollipop and after,
     * to ensure consistent dimensions on all platforms.
     *
     * @param useCompatPadding true if CardButton is adding inner padding on platforms
     * Lollipop and after, to ensure consistent dimensions on all platforms.
     * @see #getUseCompatPadding()
     */
    public void setUseCompatPadding(boolean useCompatPadding) {
        if (mCompatPadding != useCompatPadding) {
            mCompatPadding = useCompatPadding;
            getImpl().onCompatShadowChanged();
        }
    }

    /**
     * Returns whether CardButton will add inner padding on platforms Lollipop and after.
     *
     * @return true if CardButton is adding inner padding on platforms Lollipop and after,
     * to ensure consistent dimensions on all platforms.
     * @see #setUseCompatPadding(boolean)
     */
    public boolean getUseCompatPadding() {
        return mCompatPadding;
    }

    @SuppressWarnings("unused")
    public void setDrawSelectorOnTop(final boolean drawSelectorOnTop) {
        if (mDrawSelectorOnTop != drawSelectorOnTop) {
            mDrawSelectorOnTop = drawSelectorOnTop;
            updateBackgroundDrawable();
        }
    }

    @SuppressWarnings("unused")
    public boolean getDrawSelectorOnTop() {
        return mDrawSelectorOnTop;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().onDetachedFromWindow();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (mForeground != null) {
            mForeground.setVisible(visibility == VISIBLE, false);
        }
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || (who == mForeground);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        final int[] stateSet = getDrawableState();
        getImpl().onDrawableStateChanged(stateSet);
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.applySupportTint();
        }
        if (mForeground != null && mForeground.isStateful()) {
            mForeground.setState(stateSet);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
        if (mForeground != null) {
            mForeground.jumpToCurrentState();
        }
    }

    @Override
    @RequiresApi(21)
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (mForeground != null) {
            mForeground.setHotspot(x, y);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mForegroundBoundsChanged = true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        final Drawable foreground = mForeground;
        if (foreground != null) {
            if (mForegroundBoundsChanged) {
                mForegroundBoundsChanged = false;
                final Rect bounds = mForegroundBounds;
                getContentRect(bounds);
                foreground.setBounds(bounds);
            }
            foreground.draw(canvas);
        }
    }

    /**
     * Return in {@code rect} the bounds of the actual floating action button content in view-local
     * coordinates. This is defined as anything within any visible shadow.
     *
     * @return true if this view actually has been laid out and has a content rect, else false.
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean getContentRect(@NonNull Rect rect) {
        if (ViewCompat.isLaidOut(this)) {
            rect.set(0, 0, getWidth(), getHeight());
            rect.left += getEffectiveInsetLeft();
            rect.top += getEffectiveInsetTop();
            rect.right -= getEffectiveInsetRight();
            rect.bottom -= getEffectiveInsetBottom();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return in {@code rect} the bounds of the button touch target in view-local
     * coordinates. This is defined as content + content inset.
     *
     * @return true if this view actually has been laid out and has a touch rect, else false.
     */
    public boolean getTouchRect(@NonNull Rect rect) {
        if (ViewCompat.isLaidOut(this)) {
            rect.set(0, 0, getWidth(), getHeight());
            rect.left += Math.max(0, mShadowPadding.left - mContentInset.left);
            rect.top += Math.max(0, mShadowPadding.top - mContentInset.top);
            rect.right -= Math.max(0, mShadowPadding.right - mContentInset.right);
            rect.bottom -= Math.max(0, mShadowPadding.bottom - mContentInset.bottom);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the CardButton's background, minus any compatible shadow implementation.
     */
    @NonNull
    @SuppressWarnings("unused")
    public Drawable getContentBackground() {
        return getImpl().getContentBackground();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setSupportTintList(tint);
        }
    }

    @Nullable
    @Override
    public ColorStateList getSupportCompoundDrawableTintList() {
        if (mTextCompoundDrawableHelper != null) {
            return mTextCompoundDrawableHelper.getSupportTintList();
        }
        return null;
    }

    @Override
    public void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setSupportTintMode(tintMode);
        }
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportCompoundDrawableTintMode() {
        if (mTextCompoundDrawableHelper != null) {
            return mTextCompoundDrawableHelper.getSupportTintMode();
        }
        return null;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Skipping the gesture if it doesn't start in in the FAB 'content' area
                if (getTouchRect(mTouchArea)
                    && !mTouchArea.contains((int) ev.getX(), (int) ev.getY())) {
                    return false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Returns the backward compatible elevation of the CardButton.
     *
     * @return the backward compatible elevation in pixels.
     * @see #setCompatElevation(float)
     */
    @SuppressWarnings("unused")
    public float getCompatElevation() {
        return getImpl().getElevation();
    }

    /**
     * Updates the backward compatible elevation of the CardButton.
     *
     * @param elevation The backward compatible elevation in pixels.
     * @see #getCompatElevation()
     * @see #setUseCompatPadding(boolean)
     */
    @SuppressWarnings("unused")
    public void setCompatElevation(float elevation) {
        getImpl().setElevation(elevation);
    }

    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        setBackgroundTintList(tint);
    }

    @Nullable
    @Override
    public ColorStateList getSupportBackgroundTintList() {
        return getBackgroundTintList();
    }

    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        setBackgroundTintMode(tintMode);
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return getBackgroundTintMode();
    }

    private CardButtonImpl getImpl() {
        if (mImpl == null) {
            mImpl = createImpl();
        }
        return mImpl;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private CardButtonImpl createImpl() {
        final int sdk = Build.VERSION.SDK_INT;
        final CardButtonDelegate delegate = createDelegateImpl();
        if (sdk >= 21) {
            return new CardButtonLollipop(this, delegate);
        } else if (sdk >= 14) {
            return new CardButtonIcs(this, delegate);
        } else {
            return new CardButtonGingerbread(this, delegate);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private CardButtonDelegate createDelegateImpl() {
        return new CardButtonDelegateImpl();
    }

    private class CardButtonDelegateImpl implements CardButtonDelegate {
        CardButtonDelegateImpl() {
        }

        @Override
        public float getRadius() {
            return mCornerRadius;
        }

        @Override
        public boolean getDrawSelectorOnTop() {
            return mDrawSelectorOnTop;
        }

        @Override
        public void setShadowPadding(final int left, final int top, final int right, final int bottom) {
            mEatRequestLayout = true;
            mEatInvalidate = true;

            mShadowPadding.set(left, top, right, bottom);
            mForegroundBoundsChanged = true;
            final Rect contentPadding = mContentPadding;
            final Rect contentInset = mContentInset;

            final AltInsetDrawable background = (AltInsetDrawable) getBackground();
            final int left2 = Math.max(0, contentInset.left - left);
            final int top2 = Math.max(0, contentInset.top - top);
            final int right2 = Math.max(0, contentInset.right - right);
            final int bottom2 = Math.max(0, contentInset.bottom - bottom);
            background.set(left2, top2, right2, bottom2);

            setPadding(Math.max(left, contentInset.left) + contentPadding.left,
                Math.max(top, contentInset.top) + contentPadding.top,
                Math.max(right, contentInset.right) + contentPadding.right,
                Math.max(bottom, contentInset.bottom) + contentPadding.bottom);
            updateMinSize();

            mEatRequestLayout = false;
            mEatInvalidate = false;
            requestLayout();
            invalidate();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setBackgroundDrawable(@NonNull Drawable background) {
            final Drawable d = AltInsetDrawable.create(background);
            CardButton.this.superSetBackgroundDrawable(d);
        }

        @Override
        public boolean isCompatPaddingEnabled() {
            return mCompatPadding;
        }

        @Override
        public void setForegroundDrawable(@Nullable Drawable foreground) {
            CardButton.this.setForegroundCompat(foreground);
        }
    }
}
