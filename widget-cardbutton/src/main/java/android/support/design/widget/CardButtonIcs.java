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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

@RequiresApi(14)
@TargetApi(14)
class CardButtonIcs extends CardButtonGingerbread {

    private float mRotation;

    private final StateListAnimator mStateListAnimator;

    CardButtonIcs(Button view, CardButtonDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);

        mRotation = mView.getRotation();

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
    boolean requirePreDrawListener() {
        return true;
    }

    @Override
    void onPreDraw() {
        final float rotation = mView.getRotation();
        if (mRotation != rotation) {
            mRotation = rotation;
            updateFromViewRotation();
        }
    }

    private void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            // KitKat seems to have an issue with views which are rotated with angles which are
            // not divisible by 90. Worked around by moving to software rendering in these cases.
            if ((mRotation % 90) != 0) {
                if (mView.getLayerType() != View.LAYER_TYPE_SOFTWARE) {
                    mView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
            } else {
                if (mView.getLayerType() != View.LAYER_TYPE_NONE) {
                    mView.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            }
        }

        // Offset any View rotation
//        if (mShadowDrawable != null) {
//            mShadowDrawable.setRotation(-mRotation);
//        }
//        if (mBorderDrawable != null) {
//            mBorderDrawable.setRotation(-mRotation);
//        }
    }

    @Override
    void onDrawableStateChanged(int[] state) {
        super.onDrawableStateChanged(state);
        mStateListAnimator.setState(state);
    }

    @Override
    void jumpDrawableToCurrentState() {
        super.jumpDrawableToCurrentState();
        mStateListAnimator.jumpToCurrentState();
    }

    private ValueAnimator createAnimator(@NonNull ShadowAnimatorImpl impl) {
        final ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(ANIM_INTERPOLATOR);
        animator.setDuration(PRESSED_ANIM_DURATION);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(0, 1);
        return animator;
    }

    private abstract class ShadowAnimatorImpl
        extends AnimatorListenerAdapter
        implements ValueAnimator.AnimatorUpdateListener {
        private boolean mValidValues;
        private float mShadowSizeStart;
        private float mShadowSizeEnd;

        @Override
        public void onAnimationUpdate(final ValueAnimator animator) {
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
        public void onAnimationEnd(final Animator animator) {
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

}
