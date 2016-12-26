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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.StateSet;
import android.widget.Button;

class CardButtonGingerbread extends CardButtonImpl {

    private final StateListAnimator mStateListAnimator;

    ShadowDrawableWrapper mShadowDrawable;

    CardButtonGingerbread(Button view,
                          ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator animatorCreator) {
        super(view, shadowViewDelegate, animatorCreator);

        mStateListAnimator = new StateListAnimator();

        // Elevate with translationZ when pressed or focused
        mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET,
            createAnimator(new ElevateToTranslationZAnimation()));
        mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET,
            createAnimator(new ElevateToTranslationZAnimation()));
        // Reset back to elevation by default
        mStateListAnimator.addState(ENABLED_STATE_SET,
            createAnimator(new ResetElevationAnimation()));
        // Set to 0 when disabled
        mStateListAnimator.addState(EMPTY_STATE_SET,
            createAnimator(new DisabledElevationAnimation()));
    }

    @Override
    void setBackgroundDrawable(ColorStateList backgroundTint,
                               PorterDuff.Mode backgroundTintMode, int rippleColor, int borderWidth,
                               ColorStateList borderColor) {
        final float cornerRadius = mShadowViewDelegate.getRadius();

        // Now we need to tint the original background with the tint, using
        // an InsetDrawable if we have a border width
        mShapeDrawable = DrawableCompat.wrap(createShapeDrawable(cornerRadius));
        DrawableCompat.setTintList(mShapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(mShapeDrawable, backgroundTintMode);
        }

        // Now we created a mask Drawable which will be used for touch feedback.
//        GradientDrawable touchFeedbackShape = createShapeDrawable(cornerRadius);

        // We'll now wrap that touch feedback mask drawable with a ColorStateList. We do not need
        // to inset for any border here as LayerDrawable will nest the padding for us
//        mRippleDrawable = DrawableCompat.wrap(touchFeedbackShape);
//        DrawableCompat.setTintList(mRippleDrawable, createColorStateList(rippleColor));

        mRippleDrawable = createRippleDrawable(rippleColor, cornerRadius);

        final Drawable[] layers;
        if (borderWidth > 0) {
            mBorderDrawable = createBorderDrawable(borderWidth, borderColor != null ? borderColor : backgroundTint, cornerRadius);
            layers = new Drawable[]{mBorderDrawable, mShapeDrawable, mRippleDrawable};
        } else {
            mBorderDrawable = null;
            layers = new Drawable[]{mShapeDrawable, mRippleDrawable};
        }

        mContentBackground = new LayerDrawable(layers);

        mShadowDrawable = new ShadowDrawableWrapper(
            mView.getContext(),
            mContentBackground,
            cornerRadius,
            mElevation,
            mElevation + mPressedTranslationZ);
        mShadowDrawable.setAddPaddingForCorners(false);
        mShadowViewDelegate.setBackgroundDrawable(mShadowDrawable);
    }

    @Override
    void setBackgroundTintList(ColorStateList tint) {
        if (mShapeDrawable != null) {
            DrawableCompat.setTintList(mShapeDrawable, tint);
        }
        if (mBorderDrawable != null) {
            mBorderDrawable.setBorderTint(tint);
        }
    }

    @Override
    void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (mShapeDrawable != null) {
            DrawableCompat.setTintMode(mShapeDrawable, tintMode);
        }
    }

    @Override
    void setRippleColor(int rippleColor) {
//        if (mRippleDrawable != null) {
//            DrawableCompat.setTintList(mRippleDrawable, createColorStateList(rippleColor));
//        }
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
    void onDrawableStateChanged(int[] state) {
        mStateListAnimator.setState(state);
    }

    @Override
    void jumpDrawableToCurrentState() {
        mStateListAnimator.jumpToCurrentState();
    }

    @Override
    void onCompatShadowChanged() {
        // Ignore pre-v21
    }

    @Override
    void getPadding(Rect rect) {
        mShadowDrawable.getPadding(rect);
    }

    private ValueAnimatorCompat createAnimator(@NonNull ShadowAnimatorImpl impl) {
        final ValueAnimatorCompat animator = mAnimatorCreator.createAnimator();
        animator.setInterpolator(ANIM_INTERPOLATOR);
        animator.setDuration(PRESSED_ANIM_DURATION);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(0, 1);
        return animator;
    }

    private abstract class ShadowAnimatorImpl extends ValueAnimatorCompat.AnimatorListenerAdapter
        implements ValueAnimatorCompat.AnimatorUpdateListener {
        private boolean mValidValues;
        private float mShadowSizeStart;
        private float mShadowSizeEnd;

        @Override
        public void onAnimationStart(ValueAnimatorCompat animator) {
//            if (mShadowDrawable == null) {
//                animator.cancel();
//            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimatorCompat animator) {
            if (mShadowDrawable != null) {
                if (!mValidValues) {
                    mShadowSizeStart = mShadowDrawable.getShadowSize();
                    mShadowSizeEnd = getTargetShadowSize();
                    mValidValues = true;
                }

                mShadowDrawable.setShadowSize(mShadowSizeStart
                    + ((mShadowSizeEnd - mShadowSizeStart) * animator.getAnimatedFraction()));
            }
        }

        @Override
        public void onAnimationEnd(ValueAnimatorCompat animator) {
            if (mShadowDrawable != null) {
                mShadowDrawable.setShadowSize(mShadowSizeEnd);
            }
            mValidValues = false;
        }

        /**
         * @return the shadow size we want to animate to.
         */
        protected abstract float getTargetShadowSize();
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return mElevation;
        }
    }

    private class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToTranslationZAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return mElevation + mPressedTranslationZ;
        }
    }

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return 0f;
        }
    }

//    private static ColorStateList createColorStateList(int selectedColor) {
//        final int[][] states = new int[3][];
//        final int[] colors = new int[3];
//        int i = 0;
//
//        states[i] = FOCUSED_ENABLED_STATE_SET;
//        colors[i] = selectedColor;
//        i++;
//
//        states[i] = PRESSED_ENABLED_STATE_SET;
//        colors[i] = selectedColor;
//        i++;
//
//        // Default enabled state
//        states[i] = new int[0];
//        colors[i] = Color.TRANSPARENT;
//        i++;
//
//        return new ColorStateList(states, colors);
//    }

    private Drawable createRippleDrawable(@ColorInt int rippleColor, float cornerRadius) {
        GradientDrawable focused = createShapeDrawable(cornerRadius);
        focused.setColor(rippleColor);
        GradientDrawable pressed = createShapeDrawable(cornerRadius);
        pressed.setColor(rippleColor);
        GradientDrawable other = createShapeDrawable(cornerRadius);
        other.setColor(Color.TRANSPARENT);
        StateListDrawable states = new StateListDrawable();
        states.addState(FOCUSED_ENABLED_STATE_SET, focused);
        states.addState(PRESSED_ENABLED_STATE_SET, pressed);
        states.addState(StateSet.WILD_CARD, other);
        if (Build.VERSION.SDK_INT >= 11) {
            states.setEnterFadeDuration(PRESSED_ANIM_DURATION);
            states.setExitFadeDuration(PRESSED_ANIM_DURATION);
        }
        return states;
    }

}
