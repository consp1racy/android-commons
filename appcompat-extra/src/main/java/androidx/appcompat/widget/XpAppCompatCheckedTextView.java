/*
 * Copyright (C) 2014 The Android Open Source Project
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
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.TintableCheckMarkView;
import net.xpece.android.widget.TintableCompoundDrawableView;

/**
 * {@link android.widget.CheckedTextView} which supports compound drawable tint on all platforms.
 */
public class XpAppCompatCheckedTextView extends AppCompatCheckedTextView implements
    TintableCompoundDrawableView, TintableCheckMarkView {

    private XpAppCompatCompoundDrawableHelper mTextCompoundDrawableHelper;
    private XpAppCompatCheckMarkHelper mTextCheckMarkHelper;

    public XpAppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public XpAppCompatCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkedTextViewStyle);
    }

    public XpAppCompatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextCompoundDrawableHelper = new XpAppCompatCompoundDrawableHelper(this);
        mTextCompoundDrawableHelper.loadFromAttributes(attrs, defStyleAttr);

        mTextCheckMarkHelper = new XpAppCompatCheckMarkHelper(this);
        mTextCheckMarkHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.applySupportTint();
        }
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }

    @Override
    @RequiresApi(17)
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setSupportTintList(tint);
        }
    }

    @Nullable
    @Override
    public ColorStateList getSupportCompoundDrawableTintList() {
        if (mTextCompoundDrawableHelper != null) {
            return mTextCompoundDrawableHelper.getSupportTintList();
        }
        return null;
    }

    @Override
    public void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.setSupportTintMode(tintMode);
        }
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportCompoundDrawableTintMode() {
        if (mTextCompoundDrawableHelper != null) {
            return mTextCompoundDrawableHelper.getSupportTintMode();
        }
        return null;
    }

    @Override
    public void setSupportCheckMarkTintList(@Nullable final ColorStateList tint) {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.setSupportTintList(tint);
        }
    }

    @Nullable
    @Override
    public ColorStateList getSupportCheckMarkTintList() {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.getSupportTintList();
        }
        return null;
    }

    @Override
    public void setSupportCheckMarkTintMode(@Nullable final PorterDuff.Mode tintMode) {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.setSupportTintMode(tintMode);
        }
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportCheckMarkTintMode() {
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.getSupportTintMode();
        }
        return null;
    }

    @Override
    public void setCheckMarkDrawable(@Nullable final Drawable d) {
        super.setCheckMarkDrawable(d);
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }

    @Override
    public void setCheckMarkDrawable(@DrawableRes final int resId) {
        super.setCheckMarkDrawable(resId);
        if (mTextCheckMarkHelper != null) {
            mTextCheckMarkHelper.applySupportTint();
        }
    }
}
