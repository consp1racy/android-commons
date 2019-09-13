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

package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.widget.TextViewCompat;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.XpTextViewCompat;

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
                ds[0] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableLeft);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableTop)) {
                ds[1] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableTop);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableRight)) {
                ds[2] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableRight);
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableBottom)) {
                ds[3] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableBottom);
            }
            mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);

            boolean hasRelativeCompoundDrawables = false;
            ds = TextViewCompat.getCompoundDrawablesRelative(mView);
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableStart)) {
                ds[0] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableStart);
                hasRelativeCompoundDrawables = true;
            }
            if (a.hasValue(R.styleable.XpAppCompatCompoundDrawableHelper_drawableEnd)) {
                ds[2] = resolveDrawable(a, R.styleable.XpAppCompatCompoundDrawableHelper_drawableEnd);
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

    private Drawable resolveDrawable(TintTypedArray a, int index) {
        final int resId = a.getResourceId(index, 0);
        if (resId != 0) {
            final Drawable d = a.getDrawable(index);
            fixDrawable(d);
            return d;
        } else {
            return null;
        }
    }

    private Drawable resolveDrawable(int resId) {
        if (resId != 0) {
            return mDrawableManager.getDrawable(getContext(), resId);
        } else {
            return null;
        }
    }

    @Deprecated
    public void onSetCompoundDrawables(
            @Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right,
            @Nullable Drawable bottom) {
        //
    }

    @Deprecated
    public void onSetCompoundDrawablesRelative(
            @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end,
            @Nullable Drawable bottom) {
        //
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
        DrawableUtils.fixDrawable(d);
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
        ds[0] = resolveDrawable(start);
        ds[1] = resolveDrawable(top);
        ds[2] = resolveDrawable(end);
        ds[3] = resolveDrawable(bottom);
        mView.setCompoundDrawablesRelativeWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
        applySupportTint();
    }

    public void setCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes final int left, @DrawableRes final int top,
            @DrawableRes final int right, @DrawableRes final int bottom) {
        Drawable[] ds = new Drawable[4];
        ds[0] = resolveDrawable(left);
        ds[1] = resolveDrawable(top);
        ds[2] = resolveDrawable(right);
        ds[3] = resolveDrawable(bottom);
        mView.setCompoundDrawablesWithIntrinsicBounds(ds[0], ds[1], ds[2], ds[3]);
        applySupportTint();
    }
}

