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

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Px;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.Button;

import java.util.Arrays;

abstract class CardButtonImpl {

    private static final float[] TEMP_CORNER_RADII_OUT = new float[8];
    private static final float[] TEMP_CORNER_RADII_IN = new float[8];
    private static final RectF TEMP_RECTF = new RectF();

    static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int PRESSED_ANIM_DURATION = 100;
    static final int PRESSED_ANIM_DELAY = 100;

    Drawable mShapeDrawable;
    Drawable mRippleDrawable;
    Drawable mBorderDrawable;
    Drawable mContentBackground;

    float mElevation;
    float mPressedTranslationZ;

    static final int[] PRESSED_ENABLED_STATE_SET = {android.R.attr.state_pressed,
        android.R.attr.state_enabled};
    static final int[] FOCUSED_ENABLED_STATE_SET = {android.R.attr.state_focused,
        android.R.attr.state_enabled};
    static final int[] ENABLED_STATE_SET = {android.R.attr.state_enabled};
    static final int[] EMPTY_STATE_SET = new int[0];

    final Button mView;
    final ShadowViewDelegate mShadowViewDelegate;
    final ValueAnimatorCompat.Creator mAnimatorCreator;

    private final Rect mTmpRect = new Rect();
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;

    CardButtonImpl(Button view,
                   ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator animatorCreator) {
        mView = view;
        mShadowViewDelegate = shadowViewDelegate;
        mAnimatorCreator = animatorCreator;
    }

    abstract void setBackgroundDrawable(ColorStateList backgroundTint,
                                        PorterDuff.Mode backgroundTintMode, int rippleColor, int borderWidth,
                                        ColorStateList borderColor);

    abstract void setBackgroundTintList(ColorStateList tint);

    abstract void setBackgroundTintMode(PorterDuff.Mode tintMode);

    abstract void setRippleColor(int rippleColor);

    final void setElevation(float elevation) {
        if (mElevation != elevation) {
            mElevation = elevation;
            onElevationsChanged(elevation, mPressedTranslationZ);
        }
    }

    abstract float getElevation();

    final void setPressedTranslationZ(float translationZ) {
        if (mPressedTranslationZ != translationZ) {
            mPressedTranslationZ = translationZ;
            onElevationsChanged(mElevation, translationZ);
        }
    }

    abstract void onElevationsChanged(float elevation, float pressedTranslationZ);

    abstract void onDrawableStateChanged(int[] state);

    abstract void jumpDrawableToCurrentState();

    final Drawable getContentBackground() {
        return mContentBackground;
    }

    abstract void onCompatShadowChanged();

    final void updatePadding() {
        Rect rect = mTmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    abstract void getPadding(Rect rect);

    void onPaddingUpdated(Rect padding) {}

    void onAttachedToWindow() {
        if (requirePreDrawListener()) {
            ensurePreDrawListener();
            mView.getViewTreeObserver().addOnPreDrawListener(mPreDrawListener);
        }
    }

    void onDetachedFromWindow() {
        if (mPreDrawListener != null) {
            mView.getViewTreeObserver().removeOnPreDrawListener(mPreDrawListener);
            mPreDrawListener = null;
        }
    }

    boolean requirePreDrawListener() {
        return false;
    }

    Drawable createBorderDrawable(@IntRange(from = 0) @Px int borderWidth, ColorStateList backgroundTint, @FloatRange(from = 0) float cornerRadius) {
        fillRectF(borderWidth);
        Arrays.fill(TEMP_CORNER_RADII_OUT, cornerRadius);
        Arrays.fill(TEMP_CORNER_RADII_IN, Math.max(0, cornerRadius - borderWidth));
        final RoundRectShape s = new RoundRectShape(TEMP_CORNER_RADII_OUT, TEMP_RECTF, TEMP_CORNER_RADII_IN);
        final ShapeDrawable d = new ShapeDrawable(s);
        final Drawable w = DrawableCompat.wrap(d);
        DrawableCompat.setTintList(w, backgroundTint);
        return w;
    }

    void onPreDraw() {
    }

    private void ensurePreDrawListener() {
        if (mPreDrawListener == null) {
            mPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    CardButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    GradientDrawable createShapeDrawable(float cornerRadius) {
        GradientDrawable d = newGradientDrawableForShape();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setColor(Color.WHITE);
        d.setCornerRadius(cornerRadius);
        return d;
    }

    Drawable createSimpleShapeDrawable(float cornerRadius) {
        Arrays.fill(TEMP_CORNER_RADII_OUT, cornerRadius);
        final Shape s = new RoundRectShape(TEMP_CORNER_RADII_OUT, null, null);
        final ShapeDrawable d = new ShapeDrawable(s);
        d.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        return d;
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    private void fillRectF(@FloatRange(from = 0) final float stroke) {
        TEMP_RECTF.set(stroke, stroke, stroke, stroke);
    }
}
