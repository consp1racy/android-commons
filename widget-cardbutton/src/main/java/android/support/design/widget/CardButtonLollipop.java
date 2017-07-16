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

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
@TargetApi(21)
class CardButtonLollipop extends CardButtonIcs {

    private InsetDrawable mInsetDrawable;

    CardButtonLollipop(Button view, CardButtonDelegate shadowViewDelegate, ValueAnimatorCompat.Creator animatorCreator) {
        super(view, shadowViewDelegate, animatorCreator);
    }

    @Override
    void setBackgroundDrawable(@Nullable Drawable backgroundPrototype,
                               @Nullable ColorStateList backgroundTint,
                               @Nullable PorterDuff.Mode backgroundTintMode, @ColorInt int rippleColor, @IntRange(from = 0) int borderWidth,
                               @Nullable ColorStateList borderColor) {
        final float cornerRadius = mShadowViewDelegate.getRadius();

        final boolean hasBorder = borderWidth > 0 && isNotTransparent(borderColor);
        final boolean hasBackground = isNotTransparent(backgroundTint) || backgroundPrototype != null;

        if (hasBackground) {
            if (backgroundPrototype != null) {
                mShapeDrawable = DrawableCompat.wrap(backgroundPrototype);
            } else {
                mShapeDrawable = createShapeDrawable(cornerRadius);
            }
            mShapeDrawable.setTintList(backgroundTint);
            if (backgroundTintMode != null) {
                mShapeDrawable.setTintMode(backgroundTintMode);
            }
        } else {
            mShapeDrawable = null;
        }

        if (hasBorder) {
            mBorderDrawable = createBorderDrawable(borderWidth, cornerRadius, borderColor);
        } else {
            mBorderDrawable = null;
        }

        final List<Drawable> layers = new ArrayList<>();
        if (mShapeDrawable != null) layers.add(mShapeDrawable);
        if (mBorderDrawable != null) layers.add(mBorderDrawable);

        final Drawable rippleContent;
        final int size = layers.size();
        if (size == 0) {
            rippleContent = new ColorDrawable(0);
        } else if (size == 1) {
            rippleContent = layers.get(0);
        } else {
            rippleContent = new LayerDrawable(layers.toArray(new Drawable[size]));
        }
        mContentBackground = rippleContent;

        Drawable mask = createMaskDrawable(cornerRadius);
        mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), null, mask);

        mShadowViewDelegate.setBackgroundDrawable(mContentBackground);
        mShadowViewDelegate.setForegroundDrawable(mRippleDrawable);

//        final Drawable rippleContent;
//        final int size = layers.size();
//        if (size == 0) {
//            rippleContent = null;
//        } else if (size == 1) {
//            rippleContent = layers.get(0);
//        } else {
//            rippleContent = new LayerDrawable(layers.toArray(new Drawable[size]));
//        }
//
//        Drawable mask = createMaskDrawable(cornerRadius);
//        mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), rippleContent, mask);
//
//        mContentBackground = mRippleDrawable;
//
//        mShadowViewDelegate.setBackgroundDrawable(mContentBackground);
    }

    @Override
    void setRippleColor(int rippleColor) {
        if (mRippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) mRippleDrawable).setColor(ColorStateList.valueOf(rippleColor));
        } else {
            super.setRippleColor(rippleColor);
        }
    }

    @Override
    void onElevationsChanged(final float elevation, final float pressedTranslationZ) {
        try {
            final StateListAnimator stateListAnimator = new StateListAnimator();

            // Animate elevation and translationZ to our values when pressed
            AnimatorSet set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(mView, "elevation", elevation).setDuration(0))
                .with(ObjectAnimator.ofFloat(mView, View.TRANSLATION_Z, pressedTranslationZ)
                    .setDuration(PRESSED_ANIM_DURATION));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, set);

            // Same deal for when we're focused
            set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(mView, "elevation", elevation).setDuration(0))
                .with(ObjectAnimator.ofFloat(mView, View.TRANSLATION_Z, pressedTranslationZ)
                    .setDuration(PRESSED_ANIM_DURATION));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, set);

            // Animate translationZ to 0 if not pressed
            set = new AnimatorSet();
            set.playSequentially(
                ObjectAnimator.ofFloat(mView, "elevation", elevation).setDuration(0),
                // This is a no-op animation which exists here only for introducing the duration
                // because setting the delay (on the next animation) via "setDelay" or "after"
                // can trigger a NPE between android versions 22 and 24 (due to a framework
                // bug). The issue has been fixed in version 25.
                ObjectAnimator.ofFloat(mView, View.TRANSLATION_Z, mView.getTranslationZ())
                    .setDuration(PRESSED_ANIM_DELAY),
                ObjectAnimator.ofFloat(mView, View.TRANSLATION_Z, 0f)
                    .setDuration(PRESSED_ANIM_DURATION));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(ENABLED_STATE_SET, set);

            // Animate everything to 0 when disabled
            set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(mView, "elevation", 0f).setDuration(0))
                .with(ObjectAnimator.ofFloat(mView, View.TRANSLATION_Z, 0f).setDuration(0));
            set.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(EMPTY_STATE_SET, set);

            mView.setStateListAnimator(stateListAnimator);
        } catch (NullPointerException npe) {
            // Animations produce NPE in version 21. Bluntly set the values instead (matching the
            // logic in the animations below).
            if (mView.isEnabled()) {
                mView.setElevation(elevation);
                if (mView.isFocused() || mView.isPressed()) {
                    mView.setTranslationZ(pressedTranslationZ);
                } else {
                    mView.setTranslationZ(0);
                }
            } else {
                mView.setElevation(0);
                mView.setTranslationZ(0);
            }
        }

        if (mShadowViewDelegate.isCompatPaddingEnabled()) {
            updatePadding();
        }
    }

    @Override
    public float getElevation() {
        return mView.getElevation();
    }

    @Override
    void onCompatShadowChanged() {
        updatePadding();
    }

    @Override
    void onPaddingUpdated(Rect padding) {
        if (mShadowViewDelegate.isCompatPaddingEnabled()) {
            mInsetDrawable = new InsetDrawable(mContentBackground,
                padding.left, padding.top, padding.right, padding.bottom);
            mShadowViewDelegate.setBackgroundDrawable(mInsetDrawable);
        } else {
            mShadowViewDelegate.setBackgroundDrawable(mContentBackground);
        }
    }

    @Override
    void onDrawableStateChanged(int[] state) {
        // no-op
    }

    @Override
    void jumpDrawableToCurrentState() {
        // no-op
    }

    @Override
    boolean requirePreDrawListener() {
        return false;
    }

    Drawable createMaskDrawable(float cornerRadius) {
        return CardButtonDrawableFactory.newRoundRectDrawableCompat(cornerRadius, Color.WHITE);
    }

    @Override
    void getPadding(Rect rect) {
        if (mShadowViewDelegate.isCompatPaddingEnabled()) {
            final float radius = mShadowViewDelegate.getRadius();
            final float maxShadowSize = getElevation() + mPressedTranslationZ;
            final int hPadding = (int) Math.ceil(
                ShadowDrawableWrapper.calculateHorizontalPadding(maxShadowSize, radius, false));
            final int vPadding = (int) Math.ceil(
                ShadowDrawableWrapper.calculateVerticalPadding(maxShadowSize, radius, false));
            rect.set(hPadding, vPadding, hPadding, vPadding);
        } else {
            rect.set(0, 0, 0, 0);
        }
    }
}
