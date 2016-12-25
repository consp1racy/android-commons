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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.XpAppCompatCompoundDrawableHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import net.xpece.android.designextra.R;
import net.xpece.android.widget.TintableCompoundDrawableView;

@CoordinatorLayout.DefaultBehavior(CardButton.Behavior.class)
public class CardButton extends AppCompatButton implements TintableCompoundDrawableView {

    private static final String LOG_TAG = "CardButton";

    public static void setVisualMargin(final CardButton cardButton, final int left, final int top, final int right, final int bottom) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            lp.leftMargin = left - cardButton.getShadowPaddingLeft();
            lp.topMargin = top - cardButton.getShadowPaddingTop();
            lp.rightMargin = right - cardButton.getShadowPaddingRight();
            lp.bottomMargin = bottom - cardButton.getShadowPaddingBottom();
        } catch (ClassCastException ex) {
            Log.e(LOG_TAG, "Margins are not supported.");
        }
    }

    public static void setVisualMarginOriginal(final CardButton cardButton) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            lp.leftMargin -= cardButton.getShadowPaddingLeft();
            lp.topMargin -= cardButton.getShadowPaddingTop();
            lp.rightMargin -= cardButton.getShadowPaddingRight();
            lp.bottomMargin -= cardButton.getShadowPaddingBottom();
        } catch (ClassCastException ex) {
            Log.e(LOG_TAG, "Margins are not supported.");
        }
    }

    public static void setVisualMarginRelative(final CardButton cardButton, final int start, final int top, final int end, final int bottom) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(lp, start - cardButton.getShadowPaddingStart());
            lp.topMargin = top - cardButton.getShadowPaddingTop();
            MarginLayoutParamsCompat.setMarginEnd(lp, end - cardButton.getShadowPaddingEnd());
            lp.bottomMargin = bottom - cardButton.getShadowPaddingBottom();
        } catch (ClassCastException ex) {
            Log.e(LOG_TAG, "Margins are not supported.");
        }
    }

    public static void setVisualMarginRelativeOriginal(final CardButton cardButton) {
        try {
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) cardButton.getLayoutParams();
            MarginLayoutParamsCompat.setMarginStart(lp, MarginLayoutParamsCompat.getMarginStart(lp) - cardButton.getShadowPaddingStart());
            lp.topMargin -= cardButton.getShadowPaddingTop();
            MarginLayoutParamsCompat.setMarginEnd(lp, MarginLayoutParamsCompat.getMarginEnd(lp) - cardButton.getShadowPaddingEnd());
            lp.bottomMargin -= cardButton.getShadowPaddingBottom();
        } catch (ClassCastException ex) {
            Log.e(LOG_TAG, "Margins are not supported.");
        }
    }

    private ColorStateList mBackgroundTint;
    private PorterDuff.Mode mBackgroundTintMode;

    private int mBorderWidth;
    private int mRippleColor;

    private float mCornerRadius;
    final Rect mContentPadding = new Rect();
    private int mContentMinHeight;
    private int mContentMinWidth;
    private ColorStateList mBorderColor;

    private XpAppCompatCompoundDrawableHelper mTextCompoundDrawableHelper;

    boolean mCompatPadding;
    final Rect mShadowPadding = new Rect();
    private final Rect mTouchArea = new Rect();

    private CardButtonImpl mImpl;
    private boolean mSuperInit = false;

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

    @SuppressWarnings("RestrictedApi")
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mSuperInit = true;

        ThemeUtils.checkAppCompatTheme(context);

        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
            R.styleable.CardButton, defStyleAttr, defStyleRes);
        mBackgroundTint = a.getColorStateList(R.styleable.CardButton_backgroundTint);
        mBackgroundTintMode = ViewUtils.parseTintMode(a.getInt(
            R.styleable.CardButton_backgroundTintMode, -1), null);
        mRippleColor = a.getColor(R.styleable.CardButton_rippleColor, 0);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CardButton_borderWidth, 0);
        final float elevation = a.getDimension(R.styleable.CardButton_elevation, 0f);
        final float pressedTranslationZ = a.getDimension(
            R.styleable.CardButton_pressedTranslationZ, 0f);
        mCompatPadding = a.getBoolean(R.styleable.CardButton_useCompatPadding, false);

        mCornerRadius = a.getDimension(R.styleable.CardButton_carb_cornerRadius, 0f);
        int defaultPadding = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentPadding, 0);
        mContentPadding.left = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentPaddingLeft, defaultPadding);
        mContentPadding.top = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentPaddingTop, defaultPadding);
        mContentPadding.right = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentPaddingRight, defaultPadding);
        mContentPadding.bottom = a.getDimensionPixelSize(R.styleable.CardButton_carb_contentPaddingBottom, defaultPadding);
        mContentMinWidth = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentMinWidth, 0);
        mContentMinHeight = a.getDimensionPixelOffset(R.styleable.CardButton_carb_contentMinHeight, 0);
        mBorderColor = a.getColorStateList(R.styleable.CardButton_carb_borderColor);
        a.recycle();

        updateBackgroundDrawable();
        getImpl().setElevation(elevation);
        getImpl().setPressedTranslationZ(pressedTranslationZ);

        updateMinSize();

        mTextCompoundDrawableHelper = new XpAppCompatCompoundDrawableHelper(this);
        mTextCompoundDrawableHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    private void updateMinSize() {
        super.setMinWidth(mShadowPadding.left + mShadowPadding.right + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        super.setMinHeight(mShadowPadding.top + mShadowPadding.bottom + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
    }

    public int getShadowPaddingLeft() {
        return mShadowPadding.left;
    }

    public int getShadowPaddingRight() {
        return mShadowPadding.right;
    }

    public int getShadowPaddingEnd() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mShadowPadding.left;
        } else {
            return mShadowPadding.right;
        }
    }

    public int getShadowPaddingStart() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mShadowPadding.right;
        } else {
            return mShadowPadding.left;
        }
    }

    public int getShadowPaddingTop() {
        return mShadowPadding.top;
    }

    public int getShadowPaddingBottom() {
        return mShadowPadding.bottom;
    }

    @Override
    public void setMinWidth(int minWidth) {
        if (!mSuperInit) return;

        if (mContentMinWidth != minWidth) {
            mContentMinWidth = minWidth;
            super.setMinWidth(mShadowPadding.left + mShadowPadding.right + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        }
    }

    @Override
    public void setMinHeight(int minHeight) {
        if (!mSuperInit) return;

        if (mContentMinHeight != minHeight) {
            mContentMinHeight = minHeight;
            super.setMinHeight(mShadowPadding.top + mShadowPadding.bottom + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
        }
    }

    @Override
    public void setMinimumWidth(int minWidth) {
        if (!mSuperInit) return;

        if (mContentMinWidth != minWidth) {
            mContentMinWidth = minWidth;
            super.setMinimumWidth(mShadowPadding.left + mShadowPadding.right + Math.max(mContentMinWidth, mContentPadding.left + mContentPadding.right));
        }
    }

    @Override
    public void setMinimumHeight(int minHeight) {
        if (!mSuperInit) return;

        if (mContentMinHeight != minHeight) {
            mContentMinHeight = minHeight;
            super.setMinimumHeight(mShadowPadding.top + mShadowPadding.bottom + Math.max(mContentMinHeight, mContentPadding.top + mContentPadding.bottom));
        }
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        mContentPadding.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    public void setContentPaddingRelative(int start, int top, int end, int bottom) {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        final int left = rtl ? end : start;
        final int right = rtl ? start : end;
        mContentPadding.set(left, top, right, bottom);
        getImpl().updatePadding();
    }

    public int getContentPaddingLeft() {
        return mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return mContentPadding.right;
    }

    public int getContentPaddingEnd() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentPadding.left;
        } else {
            return mContentPadding.right;
        }
    }

    public int getContentPaddingStart() {
        final boolean rtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (rtl) {
            return mContentPadding.right;
        } else {
            return mContentPadding.left;
        }
    }

    public int getContentPaddingTop() {
        return mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return mContentPadding.bottom;
    }

    public void setCornerRadius(float cornerRadius) {
        if (mCornerRadius != cornerRadius) {
            mCornerRadius = cornerRadius;
            updateBackgroundDrawable();
        }
    }

    private void updateBackgroundDrawable() {
        getImpl().setBackgroundDrawable(mBackgroundTint, mBackgroundTintMode,
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
     * @attr ref android.support.design.R.styleable#CardButton_rippleColor
     * @see #getRippleColor()
     */
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

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    @Override
    public void setBackgroundResource(int resid) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    @Override
    public void setBackgroundColor(int color) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    /**
     * Set whether CardButton should add inner padding on platforms Lollipop and after,
     * to ensure consistent dimensions on all platforms.
     *
     * @param useCompatPadding true if CardButton is adding inner padding on platforms
     * Lollipop and after, to ensure consistent dimensions on all platforms.
     * @attr ref android.support.design.R.styleable#CardButton_useCompatPadding
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
     * @attr ref android.support.design.R.styleable#CardButton_useCompatPadding
     * @see #setUseCompatPadding(boolean)
     */
    public boolean getUseCompatPadding() {
        return mCompatPadding;
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
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().onDrawableStateChanged(getDrawableState());
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.applySupportTint();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
    }

    /**
     * Return in {@code rect} the bounds of the actual floating action button content in view-local
     * coordinates. This is defined as anything within any visible shadow.
     *
     * @return true if this view actually has been laid out and has a content rect, else false.
     */
    public boolean getContentRect(@NonNull Rect rect) {
        if (ViewCompat.isLaidOut(this)) {
            rect.set(0, 0, getWidth(), getHeight());
            rect.left += mShadowPadding.left;
            rect.top += mShadowPadding.top;
            rect.right -= mShadowPadding.right;
            rect.bottom -= mShadowPadding.bottom;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the CardButton's background, minus any compatible shadow implementation.
     */
    @NonNull
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
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.onSetCompoundDrawables(left, top, right, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        super.setCompoundDrawablesRelative(start, top, end, bottom);
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.onSetCompoundDrawablesRelative(start, top, end, bottom);
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
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Skipping the gesture if it doesn't start in in the FAB 'content' area
                if (getContentRect(mTouchArea)
                    && !mTouchArea.contains((int) ev.getX(), (int) ev.getY())) {
                    return false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Behavior designed for use with {@link CardButton} instances. Its main function
     * is to move {@link CardButton} views so that any displayed {@link Snackbar}s do
     * not cover them.
     */
    public static class Behavior extends CoordinatorLayout.Behavior<CardButton> {
        public Behavior() {
            super();
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
            if (lp.dodgeInsetEdges == Gravity.NO_GRAVITY) {
                // If the developer hasn't set dodgeInsetEdges, lets set it to BOTTOM so that
                // we dodge any Snackbars
                lp.dodgeInsetEdges = Gravity.BOTTOM;
            }
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, CardButton child,
                                     int layoutDirection) {
            // Now let the CoordinatorLayout lay out the FAB
            parent.onLayoutChild(child, layoutDirection);
            // Now offset it if needed
            offsetIfNeeded(parent, child);
            return true;
        }

        @Override
        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent,
                                         @NonNull CardButton child, @NonNull Rect rect) {
            // Since we offset so that any internal shadow padding isn't shown, we need to make
            // sure that the shadow isn't used for any dodge inset calculations
            final Rect shadowPadding = child.mShadowPadding;
            rect.set(child.getLeft() + shadowPadding.left,
                child.getTop() + shadowPadding.top,
                child.getRight() - shadowPadding.right,
                child.getBottom() - shadowPadding.bottom);
            return true;
        }

        /**
         * Pre-Lollipop we use padding so that the shadow has enough space to be drawn. This method
         * offsets our layout position so that we're positioned correctly if we're on one of
         * our parent's edges.
         */
        private void offsetIfNeeded(CoordinatorLayout parent, CardButton fab) {
            final Rect padding = fab.mShadowPadding;

            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                final CoordinatorLayout.LayoutParams lp =
                    (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

                int offsetTB = 0, offsetLR = 0;

                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    // If we're on the right edge, shift it the right
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    // If we're on the left edge, shift it the left
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getHeight() - lp.bottomMargin) {
                    // If we're on the bottom edge, shift it down
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    // If we're on the top edge, shift it up
                    offsetTB = -padding.top;
                }

                if (offsetTB != 0) {
                    ViewCompat.offsetTopAndBottom(fab, offsetTB);
                }
                if (offsetLR != 0) {
                    ViewCompat.offsetLeftAndRight(fab, offsetLR);
                }
            }
        }
    }

    /**
     * Returns the backward compatible elevation of the CardButton.
     *
     * @return the backward compatible elevation in pixels.
     * @attr ref android.support.design.R.styleable#CardButton_elevation
     * @see #setCompatElevation(float)
     */
    public float getCompatElevation() {
        return getImpl().getElevation();
    }

    /**
     * Updates the backward compatible elevation of the CardButton.
     *
     * @param elevation The backward compatible elevation in pixels.
     * @attr ref android.support.design.R.styleable#CardButton_elevation
     * @see #getCompatElevation()
     * @see #setUseCompatPadding(boolean)
     */
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private CardButtonImpl createImpl() {
        final int sdk = Build.VERSION.SDK_INT;
        if (sdk >= 21) {
            return new CardButtonLollipop(this, new ShadowDelegateImpl(),
                ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        } else if (sdk >= 14) {
            return new CardButtonIcs(this, new ShadowDelegateImpl(),
                ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        } else {
            return new CardButtonGingerbread(this, new ShadowDelegateImpl(),
                ViewUtils.DEFAULT_ANIMATOR_CREATOR);
        }
    }

    private class ShadowDelegateImpl implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        @Override
        public float getRadius() {
            return mCornerRadius;
        }

        @Override
        public void setShadowPadding(int left, int top, int right, int bottom) {
            mShadowPadding.set(left, top, right, bottom);
            setPadding(left + mContentPadding.left, top + mContentPadding.top,
                right + mContentPadding.right, bottom + mContentPadding.bottom);
            updateMinSize();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setBackgroundDrawable(Drawable background) {
            CardButton.super.setBackgroundDrawable(background);
        }

        @Override
        public boolean isCompatPaddingEnabled() {
            return mCompatPadding;
        }
    }
}
