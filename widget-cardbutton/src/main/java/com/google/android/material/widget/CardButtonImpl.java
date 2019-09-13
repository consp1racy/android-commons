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

package com.google.android.material.widget;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.Button;

abstract class CardButtonImpl {

    static final Interpolator ANIM_INTERPOLATOR = new FastOutLinearInInterpolator();
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
    final CardButtonDelegate mShadowViewDelegate;

    private final Rect mTmpRect = new Rect();
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;

    private boolean mHasUpdatedPadding = false;
    private final Rect mLastPadding = new Rect();

    CardButtonImpl(Button view, CardButtonDelegate shadowViewDelegate) {
        mView = view;
        mShadowViewDelegate = shadowViewDelegate;
    }

    abstract void setBackgroundDrawable(@Nullable Drawable backgroundPrototype,
                                        @Nullable ColorStateList backgroundTint,
                                        @Nullable PorterDuff.Mode backgroundTintMode, @ColorInt int rippleColor, @IntRange(from = 0) int borderWidth,
                                        @Nullable ColorStateList borderColor);

    abstract void setBackgroundTintList(@Nullable ColorStateList tint);

    abstract void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode);

    abstract void setRippleColor(@ColorInt int rippleColor);

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

    abstract void onDrawableStateChanged(@NonNull int[] state);

    abstract void jumpDrawableToCurrentState();

    final Drawable getContentBackground() {
        return mContentBackground;
    }

    abstract void onCompatShadowChanged();

    final void updatePadding() {
        Rect rect = mTmpRect;
        getPadding(rect);
        if (!rect.equals(mLastPadding) || !mHasUpdatedPadding) {
            mLastPadding.set(rect);
            mHasUpdatedPadding = true;
            onPaddingUpdated(rect);
            mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    abstract void getPadding(@NonNull Rect rect);

    void onPaddingUpdated(@NonNull Rect padding) {}

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

    static boolean isNotTransparent(@Nullable ColorStateList borderColor) {
        return borderColor != null && (borderColor.isStateful() || Color.alpha(borderColor.getDefaultColor()) > 0);
    }

}
