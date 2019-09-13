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
package com.google.android.material.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.TintAwareDrawable;

/**
 * Very simple drawable that draws a rounded rectangle background with arbitrary corners and also
 * reports proper outline for Lollipop.
 * <p>
 * Simpler and uses less resources compared to GradientDrawable or ShapeDrawable.
 */
class XpRoundRectDrawable extends Drawable implements TintAwareDrawable {
    @NonNull
    private static final RoundRectHelper sHelper;

    static {
//        final int sdk = Build.VERSION.SDK_INT;
//        if (sdk >= 17) {
            sHelper = new RoundRectHelperImpl();
//        } else {
//            sHelper = new RoundRectHelperGingerbread();
//        }
    }

    private RoundRectConstantState mState;
    private boolean mMutated = false;

    public XpRoundRectDrawable(@NonNull final ColorStateList backgroundColor, final float radius) {
        this(new RoundRectConstantState(backgroundColor, radius));
    }

    private void setBackground(@NonNull ColorStateList color) {
        mState.mBackground = color;
        onSetBackground();
    }

    private void onSetBackground() {
        final int color = getColorForState(getState());
        mState.mPaint.setColor(color);
        invalidateSelf();
    }

    private int getColorForState(@NonNull final int[] stateSet) {
        final RoundRectConstantState state = mState;
        return state.mBackground.getColorForState(stateSet, state.mBackground.getDefaultColor());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Paint paint = mState.mPaint;

        final int prevAlpha = paint.getAlpha();
        paint.setAlpha(modulateAlpha(prevAlpha, mState.mAlpha));

        final boolean clearColorFilter;
        if (mState.mTintFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(mState.mTintFilter);
            clearColorFilter = true;
        } else {
            clearColorFilter = false;
        }

        sHelper.drawRoundRect(canvas, mState.mBoundsF, mState.mRadius, paint);

        if (clearColorFilter) {
            paint.setColorFilter(null);
        }

        paint.setAlpha(prevAlpha);
    }

    private void updateBounds(@Nullable Rect bounds) {
        if (bounds == null) {
            bounds = getBounds();
        }
        mState.mBoundsF.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        mState.mBoundsI.set(bounds);
    }

    @Override
    protected void onBoundsChange(@NonNull Rect bounds) {
        super.onBoundsChange(bounds);
        updateBounds(bounds);
    }

    @Override
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        outline.setRoundRect(mState.mBoundsI, mState.mRadius);
    }

    @SuppressWarnings("unused")
    void setRadius(float radius) {
        if (radius == mState.mRadius) {
            return;
        }
        mState.mRadius = radius;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        mState.mAlpha = alpha;
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter cf) {
        mState.mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getAlpha() {
        return mState.mAlpha;
    }

    @Nullable
    @Override
    public ColorFilter getColorFilter() {
        return mState.mPaint.getColorFilter();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @SuppressWarnings("unused")
    public float getRadius() {
        return mState.mRadius;
    }

    public void setColor(@NonNull ColorStateList color) {
        setBackground(color);
        invalidateSelf();
    }

    @NonNull
    public ColorStateList getColor() {
        return mState.mBackground;
    }

    @Override
    public void setTint(@ColorInt int tint) {
        setTintList(ColorStateList.valueOf(tint));
    }

    @Override
    public void setTintList(@Nullable ColorStateList tint) {
        mState.mTint = tint;
        mState.mTintFilter = createTintFilter(mState.mTint, mState.mTintMode);
        invalidateSelf();
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        mState.mTintMode = tintMode;
        mState.mTintFilter = createTintFilter(mState.mTint, mState.mTintMode);
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(@NonNull int[] stateSet) {
        final int newColor = getColorForState(stateSet);
        final boolean colorChanged = newColor != mState.mPaint.getColor();
        if (colorChanged) {
            mState.mPaint.setColor(newColor);
        }
        if (mState.mTint != null && mState.mTintMode != null) {
            mState.mTintFilter = createTintFilter(mState.mTint, mState.mTintMode);
            return true;
        }
        return colorChanged;
    }

    @Override
    public boolean isStateful() {
        return mState.mTint != null && mState.mTint.isStateful() || mState.mBackground.isStateful() || super.isStateful();
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        mState.mChangingConfigurations = getChangingConfigurations();
        return mState;
    }

    @NonNull
    @Override
    public Drawable mutate() {
        if (!mMutated && super.mutate() == this) {
            mState = new RoundRectConstantState(mState);
            mMutated = true;
        }
        return this;
    }

    private static int modulateAlpha(int paintAlpha, int alpha) {
        int scale = alpha + (alpha >>> 7); // convert to 0..256
        return paintAlpha * scale >>> 8;
    }

    /**
     * Ensures the tint filter is consistent with the current tint color and
     * mode.
     */
    @Nullable
    private PorterDuffColorFilter createTintFilter(ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        final int color = tint.getColorForState(getState(), Color.TRANSPARENT);
        return new PorterDuffColorFilter(color, tintMode);
    }

    XpRoundRectDrawable(@NonNull final RoundRectConstantState state) {
        mState = state;
        onSetBackground();
    }

    static class RoundRectConstantState extends ConstantState {
        int mChangingConfigurations;

        final Paint mPaint;
        final RectF mBoundsF;
        final Rect mBoundsI;

        float mRadius;
        @NonNull ColorStateList mBackground;
        int mAlpha = 255;

        PorterDuffColorFilter mTintFilter;
        ColorStateList mTint;
        PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

        RoundRectConstantState(@NonNull final ColorStateList background, final float radius) {
            mRadius = radius;
            mBackground = background;

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

            mBoundsF = new RectF();
            mBoundsI = new Rect();
        }

        @SuppressWarnings("IncompleteCopyConstructor")
        RoundRectConstantState(RoundRectConstantState other) {
            this.mChangingConfigurations = other.mChangingConfigurations;

            this.mPaint = new Paint(other.mPaint);
            this.mBoundsF = new RectF(other.mBoundsF);
            this.mBoundsI = new Rect(other.mBoundsI);

            this.mRadius = other.mRadius;
            this.mBackground = other.mBackground;
            this.mAlpha = other.mAlpha;

            this.mTintFilter = other.mTintFilter;
            this.mTint = other.mTint;
            this.mTintMode = other.mTintMode;
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new XpRoundRectDrawable(this);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }
    }

    interface RoundRectHelper {
        void drawRoundRect(@NonNull Canvas canvas, @NonNull RectF bounds, float cornerRadius, @NonNull Paint paint);
    }

    // Don't use now. Produces unwanted results when working with translucent colors.
//    static class RoundRectHelperGingerbread implements RoundRectHelper {
//        final RectF sCornerRect = new RectF();
//
//        /**
//         * Draws a round rect using 7 draw operations. This is faster than using
//         * canvas.drawRoundRect before JBMR1 because API 11-16 used alpha mask textures to draw
//         * shapes.
//         */
//        @Override
//        public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
//            final float twoRadius = cornerRadius * 2;
//            final float innerWidth = bounds.width() - twoRadius - 1;
//            final float innerHeight = bounds.height() - twoRadius - 1;
//            if (cornerRadius >= 1f) {
//                // increment corner radius to account for half pixels.
//                float roundedCornerRadius = cornerRadius + .5f;
//                sCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius,
//                    roundedCornerRadius);
//                int saved = canvas.save();
//                canvas.translate(bounds.left + roundedCornerRadius,
//                    bounds.top + roundedCornerRadius);
//                canvas.drawArc(sCornerRect, 180, 90, true, paint);
//                canvas.translate(innerWidth, 0);
//                canvas.rotate(90);
//                canvas.drawArc(sCornerRect, 180, 90, true, paint);
//                canvas.translate(innerHeight, 0);
//                canvas.rotate(90);
//                canvas.drawArc(sCornerRect, 180, 90, true, paint);
//                canvas.translate(innerWidth, 0);
//                canvas.rotate(90);
//                canvas.drawArc(sCornerRect, 180, 90, true, paint);
//                canvas.restoreToCount(saved);
//                //draw top and bottom pieces
//                canvas.drawRect(bounds.left + roundedCornerRadius - 1f, bounds.top,
//                    bounds.right - roundedCornerRadius + 1f,
//                    bounds.top + roundedCornerRadius, paint);
//
//                canvas.drawRect(bounds.left + roundedCornerRadius - 1f,
//                    bounds.bottom - roundedCornerRadius,
//                    bounds.right - roundedCornerRadius + 1f, bounds.bottom, paint);
//            }
//            // center
//            canvas.drawRect(bounds.left, bounds.top + cornerRadius,
//                bounds.right, bounds.bottom - cornerRadius, paint);
//        }
//    }

    static class RoundRectHelperImpl implements RoundRectHelper {
        @Override
        public void drawRoundRect(@NonNull Canvas canvas, @NonNull RectF bounds, float cornerRadius, @NonNull Paint paint) {
            canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
        }
    }
}
