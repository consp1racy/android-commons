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

package support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cz.quickjobs.android.R;
import timber.log.Timber;

/**
 * @hide
 */
class XpAppCompatCompoundDrawableHelper {

    private final TextView mView;
    private final AppCompatDrawableManager mDrawableManager;

    private final TintInfo mInternalTint[] = new TintInfo[6];
    private TintInfo mTint;

    private final SparseArray<WeakReference<Drawable>> mDrawables = new SparseArray<>(6);

    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;
    private static final int START = 4;
    private static final int END = 5;

    public XpAppCompatCompoundDrawableHelper(@NonNull TextView view, @Nullable AppCompatDrawableManager drawableManager) {
        mView = view;
        mDrawableManager = drawableManager;

        for (int i = 0, count = 6; i < count; i++) {
            mDrawables.put(i, new WeakReference<Drawable>(null));
        }
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TintTypedArray a = null;
        try {
            a = TintTypedArray.obtainStyledAttributes(mView.getContext(), attrs,
                R.styleable.XpAppCompatTextView, defStyleAttr, 0);

            Drawable[] ds = mView.getCompoundDrawables();
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableLeft)) {
                ds[0] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableLeft, LEFT);
            }
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableTop)) {
                ds[1] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableTop, TOP);
            }
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableRight)) {
                ds[2] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableRight, RIGHT);
            }
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableBottom)) {
                ds[3] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableBottom, BOTTOM);
            }
            mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);

            boolean hasRelativeCompoundDrawables = false;
            ds = XpTextViewCompat.getCompoundDrawablesRelative(mView);
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableStart)) {
                ds[0] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableStart, START);
                hasRelativeCompoundDrawables = true;
            }
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableEnd)) {
                ds[2] = resolveDrawable(a, R.styleable.XpAppCompatTextView_drawableEnd, END);
                hasRelativeCompoundDrawables = true;
            }
            if (hasRelativeCompoundDrawables) {
                TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(mView, ds[0], ds[1], ds[2], ds[3]);
            }

            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableTint)) {
                XpTextViewCompat.setCompoundDrawableTintList(mView, a.getColorStateList(R.styleable.XpAppCompatTextView_drawableTint));
            }
            if (a.hasValue(R.styleable.XpAppCompatTextView_drawableTintMode)) {
                XpTextViewCompat.setCompoundDrawableTintMode(mView, DrawableUtils.parseTintMode(a.getInt(R.styleable.XpAppCompatTextView_drawableTintMode, -1), null));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    void setSupportTintList(ColorStateList tint) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintList = tint;
        mTint.mHasTintList = true;

        applySupportTint();
    }

    ColorStateList getSupportTintList() {
        return mTint != null ? mTint.mTintList : null;
    }

    void setSupportTintMode(PorterDuff.Mode tintMode) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintMode = tintMode;
        mTint.mHasTintMode = true;

        applySupportTint();
    }

    PorterDuff.Mode getSupportTintMode() {
        return mTint != null ? mTint.mTintMode : null;
    }

    private void onSetCompoundDrawables(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        Timber.d("Setting internal tint.");
        Drawable[] ds = mView.getCompoundDrawables();
        // Update the default background tint
        setInternalTint(ds[0], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), left) : null, LEFT);
        setInternalTint(ds[1], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), top) : null, TOP);
        setInternalTint(ds[2], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), right) : null, RIGHT);
        setInternalTint(ds[3], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), bottom) : null, BOTTOM);
    }

    private void onSetCompoundDrawablesRelative(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        Timber.d("Setting relative internal tint.");
        Drawable[] ds = XpTextViewCompat.getCompoundDrawablesRelative(mView);
        // Update the default background tint
        setInternalTint(ds[0], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), start) : null, START);
        setInternalTint(ds[1], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), top) : null, TOP);
        setInternalTint(ds[2], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), end) : null, END);
        setInternalTint(ds[3], mDrawableManager != null ? mDrawableManager.getTintList(mView.getContext(), bottom) : null, BOTTOM);
    }

    void onSetCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left == mDrawables.get(LEFT).get() &&
            top == mDrawables.get(TOP).get() &&
            right == mDrawables.get(RIGHT).get() &&
            bottom == mDrawables.get(BOTTOM).get()) {
            Timber.d("Known compound drawables, not clearing internal tint.");
        } else {
            Timber.d("Unknown compound drawables, clearing internal tint.");
            clearInternalTint();
        }
    }

    void onSetCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (start == mDrawables.get(START).get() &&
            top == mDrawables.get(TOP).get() &&
            end == mDrawables.get(END).get() &&
            bottom == mDrawables.get(BOTTOM).get()) {
            Timber.d("Known relative compound drawables, not clearing internal tint.");
        } else {
            Timber.d("Unknown relative compound drawables, clearing internal tint.");
            clearInternalTint();
        }
    }

    private void clearInternalTint() {
        for (int i = 0, count = mInternalTint.length; i < count; i++) {
            mInternalTint[i] = null;
        }
        applySupportTint();
    }

    private void setInternalTint(@Nullable Drawable d, @Nullable ColorStateList tint, int index) {
        if (tint != null) {
            if (mInternalTint[index] == null) {
                mInternalTint[index] = new TintInfo();
            }
            mInternalTint[index].mTintList = tint;
            mInternalTint[index].mHasTintList = true;
        } else {
            mInternalTint[index] = null;
        }
        if (d != null) {
            applySupportTint(d, index);
        }
    }

    void applySupportTint() {
        Drawable[] ds = mView.getCompoundDrawables();
        for (int i = 0; i < 4; i++) {
            Drawable d = ds[i];
            if (d != null) {
                applySupportTint(d, i);
            }
        }
        if (Build.VERSION.SDK_INT >= 17) {
            ds = mView.getCompoundDrawablesRelative();
            Drawable d;
            d = ds[0];
            if (d != null) {
                applySupportTint(d, START);
            }
            d = ds[2];
            if (d != null) {
                applySupportTint(d, END);
            }
        }
    }

    private void applySupportTint(@NonNull Drawable d, int i) {
        if (mTint != null) {
            AppCompatDrawableManager.tintDrawable(d, mTint, mView.getDrawableState());
        } else if (mInternalTint[i] != null) {
            AppCompatDrawableManager.tintDrawable(d, mInternalTint[i], mView.getDrawableState());
        }
    }

    void setCompoundDrawablesRelativeWithIntrinsicBounds(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        Drawable[] ds = new Drawable[4];
        ds[0] = resolveDrawable(start, 4);
        ds[1] = resolveDrawable(top, 1);
        ds[2] = resolveDrawable(end, 5);
        ds[3] = resolveDrawable(bottom, 3);
        mView.setCompoundDrawablesRelativeWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
//        onSetCompoundDrawablesRelative(start, top, end, bottom);
    }

    void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        Drawable[] ds = new Drawable[4];
        ds[0] = resolveDrawable(left, 0);
        ds[1] = resolveDrawable(top, 1);
        ds[2] = resolveDrawable(right, 2);
        ds[3] = resolveDrawable(bottom, 3);
        mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
//        onSetCompoundDrawables(left, top, right, bottom);
    }

    private Drawable resolveDrawable(@DrawableRes int resId, int index) {
        if (resId != 0) {
            final Drawable d = mDrawableManager != null
                ? mDrawableManager.getDrawable(mView.getContext(), resId)
                : ContextCompat.getDrawable(mView.getContext(), resId);
            final ColorStateList tint = mDrawableManager != null
                ? mDrawableManager.getTintList(mView.getContext(), resId)
                : null;
            setInternalTint(d, tint, index);
            if (d != null) {
                DrawableUtils.fixDrawable(d);
            }
            mDrawables.put(index, new WeakReference<>(d));
            return d;
        } else {
            setInternalTint(null, null, index);
            mDrawables.put(index, new WeakReference<Drawable>(null));
            return null;
        }
    }

    private Drawable resolveDrawable(TintTypedArray a, int resIndex, int index) {
        int resId = a.getResourceId(resIndex, 0);
        if (resId != 0) {
            final Drawable d = a.getDrawable(resIndex);
            final ColorStateList tint = mDrawableManager != null
                ? mDrawableManager.getTintList(mView.getContext(), resId)
                : null;
            setInternalTint(d, tint, index);
            if (d != null) {
                DrawableUtils.fixDrawable(d);
            }
            mDrawables.put(index, new WeakReference<>(d));
            return d;
        } else {
            setInternalTint(null, null, index);
            mDrawables.put(index, new WeakReference<Drawable>(null));
            return null;
        }
    }
}
