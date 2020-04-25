/*
 * Copyright (C) 2016 The Android Open Source Project
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

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.XpAppCompatCompoundDrawableHelper;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

import net.xpece.android.widget.TintableCompoundDrawableView;

/**
 * {@link TextInputEditText} which supports compound drawable tint on all platforms.
 */
public class XpTextInputEditText extends TextInputEditText implements TintableCompoundDrawableView {

    private XpAppCompatCompoundDrawableHelper mTextCompoundDrawableHelper;

    public XpTextInputEditText(Context context) {
        super(context);
        init(null, 0);
    }

    public XpTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public XpTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        mTextCompoundDrawableHelper = new XpAppCompatCompoundDrawableHelper(this);
        mTextCompoundDrawableHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mTextCompoundDrawableHelper != null) {
            mTextCompoundDrawableHelper.applySupportTint();
        }
    }

    @Override
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
}
