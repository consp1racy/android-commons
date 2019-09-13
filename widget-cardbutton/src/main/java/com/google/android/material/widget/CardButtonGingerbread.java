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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.graphics.drawable.DrawableCompat;
import android.util.StateSet;
import android.widget.Button;

import com.google.android.material.shadow.ShadowDrawableWrapper;

import java.util.ArrayList;
import java.util.List;

class CardButtonGingerbread extends CardButtonImpl {

    ShadowDrawableWrapper mShadowDrawable;

    CardButtonGingerbread(Button view, CardButtonDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    @Override
    void setBackgroundDrawable(@Nullable Drawable backgroundPrototype,
                               @Nullable ColorStateList backgroundTint,
                               @Nullable PorterDuff.Mode backgroundTintMode, @ColorInt int rippleColor, @IntRange(from = 0) int borderWidth,
                               @Nullable ColorStateList borderColor) {
        final boolean drawSelectorOnTop = mShadowViewDelegate.getDrawSelectorOnTop();
        final float cornerRadius = mShadowViewDelegate.getRadius();

        final boolean hasBorder = borderWidth > 0 && isNotTransparent(borderColor);
        final boolean hasBackground = isNotTransparent(backgroundTint) || backgroundPrototype != null;

        if (hasBackground) {
            if (backgroundPrototype != null) {
                mShapeDrawable = DrawableCompat.wrap(backgroundPrototype);
            } else {
                mShapeDrawable = createShapeDrawable(cornerRadius);
            }
            DrawableCompat.setTintList(mShapeDrawable, backgroundTint);
            if (backgroundTintMode != null) {
                DrawableCompat.setTintMode(mShapeDrawable, backgroundTintMode);
            }
        } else {
            mShapeDrawable = null;
        }

        if (hasBorder) {
            mBorderDrawable = createBorderDrawable(borderWidth, cornerRadius, borderColor);
        } else {
            mBorderDrawable = null;
        }

        mRippleDrawable = createRippleDrawable(rippleColor, cornerRadius);

        makeAndSetBackground(cornerRadius, drawSelectorOnTop);
    }

    private void makeAndSetBackground(final float radius, final boolean drawSelectorOnTop) {
        final List<Drawable> layers = new ArrayList<>();
        if (mShapeDrawable != null) layers.add(mShapeDrawable);
        if (mBorderDrawable != null) layers.add(mBorderDrawable);
        if (!drawSelectorOnTop && mRippleDrawable != null) layers.add(mRippleDrawable);

        final int size = layers.size();
        if (size > 1) {
            mContentBackground = new LayerDrawable(layers.toArray(new Drawable[size]));
        } else if (size == 1) {
            mContentBackground = layers.get(0);
        } else {
            mContentBackground = new ColorDrawable(0);
        }

        mShadowDrawable = new ShadowDrawableWrapper(
            mView.getContext(),
            mContentBackground,
            radius,
            mElevation,
            mElevation + mPressedTranslationZ);
        mShadowDrawable.setAddPaddingForCorners(false);
        mShadowViewDelegate.setBackgroundDrawable(mShadowDrawable);

        if (drawSelectorOnTop) {
            mShadowViewDelegate.setForegroundDrawable(mRippleDrawable);
        } else {
            mShadowViewDelegate.setForegroundDrawable(null);
        }
    }

    @NonNull
    Drawable createShapeDrawable(float cornerRadius) {
        return CardButtonDrawableFactory.newRoundRectDrawableCompat(cornerRadius, Color.WHITE);
    }

    @NonNull
    Drawable createBorderDrawable(@IntRange(from = 0) @Px final int borderWidth, @FloatRange(from = 0) final float cornerRadius, @NonNull final ColorStateList borderTint) {
        final Drawable drawable;
        if (mView.isInEditMode()) {
            drawable = CardButtonDrawableFactory.newBorderShapeDrawableCompatForEditMode(borderWidth, cornerRadius);
            final int color = borderTint.getColorForState(mView.getDrawableState(), borderTint.getDefaultColor());
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            drawable = CardButtonDrawableFactory.newBorderShapeDrawableCompat(borderWidth, cornerRadius);
            DrawableCompat.setTintList(drawable, borderTint);
        }
        return drawable;
    }

    @Override
    void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (mShapeDrawable != null) {
            DrawableCompat.setTintList(mShapeDrawable, tint);
        }
    }

    @Override
    void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mShapeDrawable != null) {
            DrawableCompat.setTintMode(mShapeDrawable, tintMode);
        }
    }

    @Override
    void setRippleColor(@ColorInt int rippleColor) {
        final boolean drawSelectorOnTop = mShadowViewDelegate.getDrawSelectorOnTop();
        final float radius = mShadowViewDelegate.getRadius();
        mRippleDrawable = createRippleDrawable(rippleColor, radius);
        makeAndSetBackground(radius, drawSelectorOnTop);
    }

    @Override
    float getElevation() {
        return mElevation;
    }

    @Override
    void onElevationsChanged(float elevation, float pressedTranslationZ) {
        if (mShadowDrawable != null) {
            mShadowDrawable.setShadowSize(elevation, elevation + mPressedTranslationZ);
            updatePadding();
        }
    }

    @Override
    void onDrawableStateChanged(@NonNull final int[] state) {
        // Ignore on Gingerbread
    }

    @Override
    void jumpDrawableToCurrentState() {
        // Ignore on Gingerbread
    }

    @Override
    void onCompatShadowChanged() {
        // Ignore pre-v21
    }

    @Override
    void getPadding(@NonNull final Rect rect) {
        mShadowDrawable.getPadding(rect);
    }

    private Drawable createRippleDrawable(@ColorInt int rippleColor, float cornerRadius) {
        Drawable focused = CardButtonDrawableFactory.newRoundRectDrawableCompat(cornerRadius, rippleColor);
        Drawable pressed = CardButtonDrawableFactory.newRoundRectDrawableCompat(cornerRadius, rippleColor);
        Drawable other = CardButtonDrawableFactory.newRoundRectDrawableCompat(cornerRadius, Color.TRANSPARENT);
        StateListDrawable states = new StateListDrawable();
        states.addState(FOCUSED_ENABLED_STATE_SET, focused);
        states.addState(PRESSED_ENABLED_STATE_SET, pressed);
        states.addState(StateSet.WILD_CARD, other);
        states.setEnterFadeDuration(PRESSED_ANIM_DURATION);
        states.setExitFadeDuration(PRESSED_ANIM_DURATION);
        return states;
    }

}
