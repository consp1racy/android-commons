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

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.XpCheckedTextViewCompat;

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.GROUP_ID)
@SuppressWarnings("RestrictedApi")
public final class XpAppCompatCheckMarkHelper {

    private final CheckedTextView mView;

    private TintInfo mTint;

    public XpAppCompatCheckMarkHelper(@NonNull final CheckedTextView view) {
        mView = view;
    }

    public void loadFromAttributes(@Nullable final AttributeSet attrs, final int defStyleAttr) {
        TintTypedArray a = null;
        try {
            a = TintTypedArray.obtainStyledAttributes(mView.getContext(), attrs,
                    R.styleable.XpAppCompatCheckMarkHelper, defStyleAttr, 0);

            if (a.hasValue(R.styleable.XpAppCompatCheckMarkHelper_checkMark)) {
                mView.setCheckMarkDrawable(
                        a.getResourceId(R.styleable.XpAppCompatCheckMarkHelper_checkMark, 0));
            }
            if (a.hasValue(R.styleable.XpAppCompatCheckMarkHelper_checkMarkTint)) {
                XpCheckedTextViewCompat.setCheckMarkTintList(mView,
                        a.getColorStateList(R.styleable.XpAppCompatCheckMarkHelper_checkMarkTint));
            }
            if (a.hasValue(R.styleable.XpAppCompatCheckMarkHelper_checkMarkTintMode)) {
                XpCheckedTextViewCompat.setCheckMarkTintMode(mView, DrawableUtils.parseTintMode(
                        a.getInt(R.styleable.XpAppCompatCheckMarkHelper_checkMarkTintMode, -1),
                        null));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public void applySupportTint() {
        final Drawable d = XpCheckedTextViewCompat.getCheckMarkDrawable(mView);
        if (d != null) {
            applySupportTint(d);
        }
    }

    private void applySupportTint(@NonNull final Drawable d) {
        DrawableUtils.fixDrawable(d);
        if (mTint != null) {
            AppCompatDrawableManager.tintDrawable(d, mTint, mView.getDrawableState());
        }
    }

    public void setSupportTintList(final ColorStateList tint) {
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

    public void setSupportTintMode(final PorterDuff.Mode tintMode) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintMode = tintMode;
        mTint.mHasTintMode = true;

        applySupportTint();
    }

    @Nullable
    public PorterDuff.Mode getSupportTintMode() {
        return mTint != null ? mTint.mTintMode : null;
    }
}

