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

package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.XpTextViewCompat;

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.GROUP_ID)
@SuppressWarnings("RestrictedApi")
public final class XpAppCompatCompoundDrawableHelper {

    private final TextView mView;
    private final AppCompatDrawableManager mDrawableManager;

    private TintInfo mTint;

    public XpAppCompatCompoundDrawableHelper(@NonNull final TextView view) {
        mView = view;
        mDrawableManager = AppCompatDrawableManager.get();
    }

    public void loadFromAttributes(@Nullable final AttributeSet attrs, final int defStyleAttr) {
        TintTypedArray a = null;
        try {
            a = TintTypedArray.obtainStyledAttributes(mView.getContext(), attrs,
                    R.styleable.XpAppCompatCompoundDrawableHelper, defStyleAttr, 0);

            Drawable[] ds = mView.getCompoundDrawables();
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableLeft)) {
                ds[0] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableLeft);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTop)) {
                ds[1] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTop);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableRight)) {
                ds[2] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableRight);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableBottom)) {
                ds[3] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableBottom);
            }
            fixDrawables(ds);
            mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);

            boolean hasRelativeCompoundDrawables = false;
            ds = XpTextViewCompat.getCompoundDrawablesRelative(mView);
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableStart)) {
                ds[0] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableStart);
                fixDrawable(ds[0]);
                hasRelativeCompoundDrawables = true;
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableEnd)) {
                ds[2] = a.getDrawable(R.styleable.XpAppCompatCompoundDrawableHelper_drawableEnd);
                fixDrawable(ds[2]);
                hasRelativeCompoundDrawables = true;
            }
            if (hasRelativeCompoundDrawables) {
                TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(mView, ds[0], ds[1], ds[2], ds[3]);
            }

            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTint)) {
                XpTextViewCompat.setCompoundDrawableTintList(mView, a.getColorStateList(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTint));
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTintMode)) {
                XpTextViewCompat.setCompoundDrawableTintMode(mView, DrawableUtils.parseTintMode(a.getInt(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTintMode, -1), null));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public void setSupportTintList(@Nullable final ColorStateList tint) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintList = tint;
        mTint.mHasTintList = true;

        applySupportTint();
    }

    @Nullable
    public ColorStateList getSupportTintList() {
        return mTint != null ? mTint.mTintList : null;
    }

    public void setSupportTintMode(@Nullable final PorterDuff.Mode tintMode) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintMode = tintMode;
        mTint.mHasTintMode = true;

        applySupportTint();
    }

    public PorterDuff.Mode getSupportTintMode() {
        return mTint != null ? mTint.mTintMode : null;
    }

    public void applySupportTint() {
        Drawable[] ds = mView.getCompoundDrawables();
        fixDrawables(ds);
        for (int i = 0; i < 4; i++) {
            Drawable d = ds[i];
            if (d != null) {
                applySupportTint(d);
            }
        }
        if (Build.VERSION.SDK_INT >= 17) {
            ds = mView.getCompoundDrawablesRelative();
            Drawable d;
            d = ds[0];
            if (d != null) {
                applySupportTint(d);
            }
            d = ds[2];
            if (d != null) {
                applySupportTint(d);
            }
        }
    }

    private void applySupportTint(@NonNull final Drawable d) {
        if (mTint != null) {
            AppCompatDrawableManager.tintDrawable(d, mTint, mView.getDrawableState());
        }
    }

    @NonNull
    private Context getContext() {
        return mView.getContext();
    }

    private void fixDrawables(@NonNull final Drawable[] drawables) {
        for (final Drawable d : drawables) {
            fixDrawable(d);
        }
    }

    private void fixDrawable(@Nullable final Drawable d) {
        if (d != null) DrawableUtils.fixDrawable(d);
    }

    @RequiresApi(17)
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes final int start, @DrawableRes final int top,
            @DrawableRes final int end, @DrawableRes final int bottom) {
        Drawable[] ds = new Drawable[4];
        ds[0] = mDrawableManager.getDrawable(getContext(), start);
        ds[1] = mDrawableManager.getDrawable(getContext(), top);
        ds[2] = mDrawableManager.getDrawable(getContext(), end);
        ds[3] = mDrawableManager.getDrawable(getContext(), bottom);
        fixDrawables(ds);
        mView.setCompoundDrawablesRelativeWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
        applySupportTint();
    }

    public void setCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes final int left, @DrawableRes final int top,
            @DrawableRes final int right, @DrawableRes final int bottom) {
        Drawable[] ds = new Drawable[4];
        ds[0] = mDrawableManager.getDrawable(getContext(), left);
        ds[1] = mDrawableManager.getDrawable(getContext(), top);
        ds[2] = mDrawableManager.getDrawable(getContext(), right);
        ds[3] = mDrawableManager.getDrawable(getContext(), bottom);
        fixDrawables(ds);
        mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
        applySupportTint();
    }
}

